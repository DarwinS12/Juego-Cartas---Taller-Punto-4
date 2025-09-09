
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN = 10;
    private final int SEPARACION = 40;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        /*
         * for (int i = 0; i < TOTAL_CARTAS; i++) {
         * Carta carta=cartas[i];
         * carta.mostrar(pnl, , );
         * }
         */
        int posicion = MARGEN + TOTAL_CARTAS * SEPARACION;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= SEPARACION;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String resultado = "No se encontraron grupos";
        // calcular los contadres de las cartas
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        // validar si hubo grupos
        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupos = true;
                break;
            }
        }

        // obtener los grupos
        if (hayGrupos) {
            resultado = "Se hallaron los siguientes grupos:\n";

            for (int i = 0; i < contadores.length; i++) {
                int contador = contadores[i];
                if (contador >= 2) {
                    resultado += Grupo.values()[contador] + " de " + NombreCarta.values()[i] + "\n";
                }

            }
        }

        return resultado;
    }

    public String getEscalerasMismaPinta() {
        boolean[][] cartasPorPinta = new boolean[4][13];
        for (Carta carta : cartas) {
            cartasPorPinta[carta.getPinta().ordinal()][carta.getNombre().ordinal()] = true;
        }

        StringBuilder resultado = new StringBuilder();

        for (int pinta = 0; pinta < 4; pinta++) {
            int inicioEscaleraFinal = -1, largoEscaleraFinal = 0;
            int inicioEscaleraActual = -1, tamañoEscaleraActual = 0;

            for (int r = 0; r <= 13; r++) {
                boolean cartaPresente = (r < 13) ? cartasPorPinta[pinta][r] : cartasPorPinta[pinta][0];

                if (cartaPresente) {
                    inicioEscaleraActual = (tamañoEscaleraActual == 0) ? r : inicioEscaleraActual;
                    tamañoEscaleraActual++;
                } else {
                    if (tamañoEscaleraActual > largoEscaleraFinal) {
                        largoEscaleraFinal = tamañoEscaleraActual;
                        inicioEscaleraFinal = inicioEscaleraActual;
                    }
                    tamañoEscaleraActual = 0;
                }
            }

            if (largoEscaleraFinal >= 3) {
                resultado.append(Grupo.values()[largoEscaleraFinal])
                        .append(" de ").append(Pinta.values()[pinta]).append(": ");
                for (int i = 0; i < largoEscaleraFinal; i++) {
                    int idx = (inicioEscaleraFinal + i) % 13;
                    resultado.append(i > 0 ? ", " : "").append(NombreCarta.values()[idx]);
                }
                resultado.append("\n");
            }
        }

        return resultado.length() == 0
                ? "No se encontraron escaleras de la misma pinta"
                : resultado.toString();
    }

    public int Puntaje() {
        //antes tomamos el enfoque de sacar el puntaje total y luego restarle los puntajes de las parejas y escaleras//
        //pero nos dimos cuenta de el enfoque que ver cuales cartas no formaban parte de una pareja o escalera era mas facil//
        int puntaje = 0;
        int residuo;
        int i = 0;
        int[] contadores1 = new int[NombreCarta.values().length];
        int[][] valoresCartas = new int[TOTAL_CARTAS][2];
        ArrayList<Carta> cartasParejas = new ArrayList<>();
        ArrayList<Carta> cartasEscalera = new ArrayList<>();
        ArrayList<Carta> cartasIndividuales = new ArrayList<>();

        for (Carta c : cartas) {
            residuo = c.getIndice() % 13;
            if (residuo == 0) {
                residuo = 13;
            }
            valoresCartas[i][0] = residuo;
            valoresCartas[i][1] = c.getPinta().ordinal();
            i += 1;
        }
        //sacamos las cartas que estan en las parejas//
        for (Carta c : cartas) {
            contadores1[c.getNombre().ordinal()]++;
            if (contadores1[c.getNombre().ordinal()] >= 2) {
                for (Carta carta : cartas) {
                    if (carta.getNombre().ordinal() == c.getNombre().ordinal() && !cartasParejas.contains(carta)) {
                        cartasParejas.add(carta);
                    }
                }
            }
        }
        for (Carta c : cartasParejas) {
            System.out.println(c.getNombre() + " " + c.getPinta());
        }
        //vemos que cartas estan en escalera//
        for (i = 0; i < valoresCartas.length; i++) {
            for (int j = 0; j < valoresCartas.length - 1; j++) {
                if (valoresCartas[j][1] > valoresCartas[j + 1][1] || (valoresCartas[j][1] == valoresCartas[j + 1][1]
                        && valoresCartas[j][0] > valoresCartas[j + 1][0])) {
                    int numeroTemporal = valoresCartas[j + 1][0];
                    int pintaTemporal = valoresCartas[j + 1][1];
                    valoresCartas[j + 1][0] = valoresCartas[j][0];
                    valoresCartas[j][0] = numeroTemporal;
                    valoresCartas[j + 1][1] = valoresCartas[j][1];
                    valoresCartas[j][1] = pintaTemporal;

                }
            }
        }
        for (i = 0; i < valoresCartas.length; i++) {
            int longitudEscalera = 1;
            ArrayList<Carta> escaleraTemporal = new ArrayList<>();
            for (Carta carta : cartas) {
                if (carta.getIndice() % 13 == valoresCartas[i][0] && carta.getPinta().ordinal() == valoresCartas[i][1]
                        && !cartasEscalera.contains(carta)) {
                    escaleraTemporal.add(carta);
                    break;
                }
            }
            for (int j = i; j < valoresCartas.length - 1; j++) {
                if (valoresCartas[j][0] + 1 == valoresCartas[j + 1][0] && valoresCartas[j][1] == valoresCartas[j + 1][1]) {
                    longitudEscalera++;
                    for (Carta carta : cartas) {
                        if (carta.getIndice() % 13 == valoresCartas[j][0] + 1 && carta.getPinta().ordinal() == valoresCartas[j][1]
                                && !cartasEscalera.contains(carta)) {
                            escaleraTemporal.add(carta);
                        }
                    }
                } else {
                    break;
                }
            }
            if (longitudEscalera > 1) {
                cartasEscalera.addAll(escaleraTemporal);
                i += longitudEscalera - 1;
            }
        }
        for (Carta c : cartasEscalera) {
            System.out.println(c.getNombre() + " " + c.getPinta());
        }
        for (Carta carta : cartas) {
            if (!cartasEscalera.contains(carta) && !cartasParejas.contains(carta)) {
                cartasIndividuales.add(carta);
            }
        }
        System.out.println("Cartas individuales");
        for (Carta c : cartasIndividuales) {
            System.out.println(c.getNombre() + " " + c.getPinta());
        }

        int valorCartasIndividuales = 0;
        for (Carta c : cartasIndividuales) {
            int valorCarta = c.getIndice() % 13;
            if (valorCarta == 0) {
                valorCarta = 13;
            }
            if (valorCarta == 1 || valorCarta >= 11) {
                valorCartasIndividuales += 10;
            } else {
                valorCartasIndividuales += valorCarta;
            }
        }

        puntaje = valorCartasIndividuales;
        return puntaje;
    }
}

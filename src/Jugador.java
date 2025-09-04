
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

}

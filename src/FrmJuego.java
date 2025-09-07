
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private JPanel pnlJugador1, pnlJugador2;
    private Jugador jugador1, jugador2;
    private JTabbedPane tpJugadores;

    public FrmJuego() {

        setSize(600, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        getContentPane().add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        getContentPane().add(btnVerificar);

        JButton btnPuntaje = new JButton("Puntaje");
        btnPuntaje.setBounds(230, 10, 100, 25);
        getContentPane().add(btnPuntaje); //Aqui agregamos el boton puntaje

        pnlJugador1 = new JPanel();
        pnlJugador1.setLayout(null);
        pnlJugador1.setBackground(new Color(150, 255, 50));

        pnlJugador2 = new JPanel();
        pnlJugador2.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 255, 255));

        tpJugadores = new JTabbedPane();
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raúl Vidal", pnlJugador2);

        tpJugadores.setBounds(10, 40, 550, 200);
        getContentPane().add(tpJugadores);

        // Agregar los eventos
        btnRepartir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repartir();
            }

        });

        btnVerificar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                verificar();
            }

        });
        btnPuntaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               btnPuntajeClick(e); //evento del boton puntaje
            }
        });




        jugador1 = new Jugador();
        jugador2 = new Jugador();

    }

    private void repartir() {
        // repartir cartas
        jugador1.repartir();
        jugador2.repartir();

        // mostrar las cartas de cada jugador
        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void verificar() {
        Jugador j = (tpJugadores.getSelectedIndex() == 0) ? jugador1 : jugador2;
        String msg = j.getGrupos() + "\n\n" + j.getEscalerasMismaPinta();
        JOptionPane.showMessageDialog(null, msg);
    }
    private void btnPuntajeClick(ActionEvent evt3) {
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.Puntaje());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.Puntaje());
                break;  //accion del boton puntaje 
        }
    }
}


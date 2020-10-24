import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main(){
        setTitle("Schiffe Versenken");
        setSize(600, 450);  // größe des Fensters fest legen
        setLocationRelativeTo(null); //Setzt das Fenster in der Mitte
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Schließst das Fenster
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {     //Ereignisbehandlung
            @Override
            public void run() {
                Main m = new Main();
                m.setVisible(true);             //macht das Fenster mit den Info aus der Klasse Main sichtbar
            }
        });
    }
}


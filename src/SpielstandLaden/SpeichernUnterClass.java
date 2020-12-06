package SpielstandLaden;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
 * Erstellen eines Fensters mit einer automatischen Groesse
 */
public class SpeichernUnterClass  extends JFrame {

    public SpeichernUnterClass() {
        JButton butt = new JButton("Speichern unter...");
        butt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAs(null);
            }
        });
        this.add(butt, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("Speichern unter Demo");
        this.setVisible(true);
    }

    boolean saveAs(String pfad) {

        JFileChooser chooser;
        if (pfad == null)
            pfad = System.getProperty("user.home");
        File file = new File(pfad.trim());

        chooser = new JFileChooser(pfad);
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        FileNameExtensionFilter plainFilter = new FileNameExtensionFilter(
                "Plaintext: txt", "txt");
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.setFileFilter(plainFilter);
        chooser.setDialogTitle("Speichern unter...");
        chooser.setVisible(true);

        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            pfad = chooser.getSelectedFile().toString();
            file = new File(pfad);
            if (plainFilter.accept(file))
                System.out.println(pfad + " kann gespeichert werden.");
            else
                System.out.println(pfad + " ist der falsche Dateityp.");

            chooser.setVisible(false);
            return true;
        }
        chooser.setVisible(false);
        return false;
    }

    public static void main(String[] args) {
        new SpeichernUnterClass();
    }
}
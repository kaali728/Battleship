package SpielstandLaden;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SpeichernUnterClass  extends JFrame {

    public boolean saveAs(String pfad) {
        JFileChooser choosePath;
        File file;
        FileWriter fw;
        BufferedWriter bw;

        if (pfad == null) {
            pfad = System.getProperty("user.home");
        }

        choosePath = new JFileChooser(pfad);
        choosePath.setDialogType(JFileChooser.SAVE_DIALOG);
        FileNameExtensionFilter plainFilter = new FileNameExtensionFilter("txt", "txt");
        choosePath.removeChoosableFileFilter(choosePath.getAcceptAllFileFilter());
        choosePath.setFileFilter(plainFilter);

        int result = choosePath.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try{
                pfad = choosePath.getSelectedFile().toString();

                String ship = "Schiffe die in der Txt ";

                file =new File(pfad);

                if(!file.exists()){
                    file.createNewFile();
                }

                fw = new FileWriter(file);

                bw = new BufferedWriter(fw);
                bw.write(ship);

                bw.close();

            }catch(IOException e){
                System.out.println("Fehler");
                e.printStackTrace();
            }
            choosePath.setVisible(false);
            return true;
        }
        choosePath.setVisible(false);
        return false;
    }
}
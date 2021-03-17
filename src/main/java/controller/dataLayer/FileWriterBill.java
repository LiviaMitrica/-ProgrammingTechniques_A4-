package controller.dataLayer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriterBill {
    public FileWriterBill() {
    }

    public void writeFile(String string, String file) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file+".txt", false);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(string);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package RW;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {


    private static CSVWriter single_instance = null;

    // private constructor restricted to this class itself
    private CSVWriter() {}

    public void write(File csvFile, String str) throws IOException {
        FileWriter csvWriter = new FileWriter(csvFile);
        csvWriter.append(str+",");
    }

    public void nextLine(File csvFile) throws IOException {
        FileWriter csvWriter = new FileWriter(csvFile);
        csvWriter.append("\n");
    }

    // static method to create instance of Singleton class
    public static CSVWriter getInstance()
    {
        if (single_instance == null)
            single_instance = new CSVWriter();

        return single_instance;
    }



}

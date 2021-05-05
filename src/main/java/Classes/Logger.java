package Classes;

import RW.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final Logger instance = new Logger();

    private Logger(){}

    public static Logger getInstance(){
        return instance;
    }


    public void log(String log) throws IOException {

        try (FileWriter writer = new FileWriter("src/main/java/logs.csv", true)) {

            StringBuilder sb = new StringBuilder();
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new java.util.Date());


            sb.append(log + ',' + timeStamp +  '\n');


            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }


}

package RW;

import Classes.*;

import java.io.*;
import java.util.Scanner;


public class CSVWriter<T> {

    private static final CSVWriter instance = new CSVWriter();

    private CSVWriter(){}

    public static CSVWriter getInstance(){
        return instance;
    }

    private int getLastId() throws FileNotFoundException {


        BufferedReader br = null;
        String[] parts = {""};
        try {

            br = new BufferedReader(new FileReader("src/main/java/accs.csv"));
            String sCurrentLine;

            String lastLine = "";

            while ((sCurrentLine = br.readLine()) != null)
                lastLine = sCurrentLine;


            parts = lastLine.split(",");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (parts[0].equals(""))
            return -1;
        else
            return Integer.parseInt(parts[0]);

    }

    public void write(T obj) throws IOException {


        if (obj.getClass() == User.class)
            try (FileWriter writer = new FileWriter("src/main/java/accs.csv", true)) {

                StringBuilder sb = new StringBuilder();


                int id = getLastId();
                if (id == -1)
                    sb.append(((User) obj).getId());
                else
                    sb.append(getLastId() + 1);

                sb.append(',');


                sb.append(((User) obj).getName());
                sb.append(',');
                sb.append(((User) obj).getEmail());
                sb.append(',');
                sb.append(((User) obj).getDob());
                sb.append(',');
                sb.append(((User) obj).getBalance());
                sb.append(',');
                sb.append(((User) obj).getRank());
                sb.append('\n');

                writer.write(sb.toString());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }

        if (obj.getClass() == Credentials.class)
            try (FileWriter writer = new FileWriter("src/main/java/creds.csv", true)) {

                StringBuilder sb = new StringBuilder();

                int id = getLastId();
                if (id == -1)
                    sb.append(((User) obj).getId());
                else
                    sb.append(getLastId());

                sb.append(',');
                sb.append(((Credentials) obj).getPasswd());
                sb.append('\n');

                writer.write(sb.toString());

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }

        if (obj.getClass() == Transaction.class)
            try (FileWriter writer = new FileWriter("src/main/java/transactions.csv", true)) {

                //do smth

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }

        if (obj.getClass() == Auction.class)
            try (FileWriter writer = new FileWriter("src/main/java/auctions.csv", true)) {

                //do smth

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }


    }

}

package RW;

import Classes.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Object;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CSVReader<T> {

    private static final CSVReader instance = new CSVReader();

    private CSVReader(){}

    public static CSVReader getInstance(){
        return instance;
    }


    public List<T> readUser(T obj) {
        BufferedReader br = null;
        String[] parts = {""};
        List<T> lst = new ArrayList<>();
        User usr = new User();
        try {

            br = new BufferedReader(new FileReader("src/main/java/accs.csv"));
            String sCurrentLine;

            String lastLine = "";

            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;


                parts = lastLine.split(",");


                lst.add((T) new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), Integer.parseInt(parts[5])));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return lst;
    }

    public List<T> readCreds(T obj) {
        BufferedReader br = null;
        String[] parts = {""};
        List<T> lst = new ArrayList<>();
        User usr = new User();
        try {

            br = new BufferedReader(new FileReader("src/main/java/creds.csv"));
            String sCurrentLine;

            String lastLine = "";

            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;


                parts = lastLine.split(",");


                lst.add((T) new Credentials(Integer.parseInt(parts[0]), parts[1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return lst;
    }

    public List<T> readTransactions(T obj) {
        BufferedReader br = null;
        String[] parts = {""};
        List<T> lst = new ArrayList<>();
        User usr = new User();
        try {

            br = new BufferedReader(new FileReader("src/main/java/accs.csv"));
            String sCurrentLine;

            String lastLine = "";

            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;


                parts = lastLine.split(",");


                lst.add((T) new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), Integer.parseInt(parts[5])));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return lst;
    }

    public List<T> readAuctions(T obj) {

        BufferedReader br = null;
        String[] parts = {""};
        List<T> lst = new ArrayList<>();
        User usr = new User();
        Object object = new Classes.Object();
        List <Classes.Object> objects = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader("src/main/java/auctions.csv"));
            String sCurrentLine;

            String lastLine = "";

            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;


                parts = lastLine.split(",");

                object = new Classes.Object(parts[1]);
                objects.add((Classes.Object) object);

                String object1 = parts[1];

                List<String> lst2 = new ArrayList<>();
                lst2.add(parts[1]);
                lst.add((T) new Auction(Integer.parseInt(parts[0]), object1, parts[2], Integer.parseInt(parts[3])));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return lst;
    }


    public List<T> read(T obj, File file) {

        List<T> lst = null;

        if (obj.getClass() == User.class)
            lst = readUser(obj);

        if(obj.getClass() == Credentials.class)
            lst = readCreds(obj);

        if(obj.getClass() == Transaction.class)
            lst = readTransactions(obj);

        if(obj.getClass() == Auction.class)
            lst = readAuctions(obj);

        return lst;

    }

}

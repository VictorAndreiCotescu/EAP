import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import Classes.*;
import RW.*;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {

        CSVReader reader = CSVReader.getInstance();

        User user = new User();
        Credentials creds = new Credentials();
        Transaction transaction = new Transaction();

        List <User> users = null;

        File usersFile = new File ("src/main/java/accs.csv");



        for(int i = 0; i < 3; ++i)
            users = reader.read(user, usersFile);

        for(int i = 0; i < users.size(); ++i)
            System.out.println(users.get(i).getId());


        Manager manager = new Manager();
        manager.system();

    }


}

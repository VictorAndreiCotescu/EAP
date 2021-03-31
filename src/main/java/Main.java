import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        List<User> users = new ArrayList<>();
        List<Credentials> creds = new ArrayList<>();



        try {
            manager.createUser(users, creds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        manager.logIn();

    }


}

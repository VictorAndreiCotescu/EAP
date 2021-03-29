import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Manager {


    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public void createUser(List<User> users, List<Credentials> creds) throws ParseException {


        Scanner cin = new Scanner(System.in);
        System.out.println("Email: ");
        String _email = cin.nextLine();
        while(!isValidEmailAddress(_email)) {
            System.err.println("Email not ok. Try Again.");
            System.out.println("Email: ");
            _email = cin.nextLine();
        }

        System.out.println("Name: ");
        String _name = cin.nextLine();

        System.out.println("Set password: ");
        String _passwd = cin.nextLine();
        System.out.println("Confirm password: ");
        String _passwdCheck = cin.nextLine();
        while(!_passwd.equals(_passwdCheck)){
            System.err.println("Password must match! Try Again.");
            System.out.println("Set password: ");
            _passwd = cin.nextLine();
            System.out.println("Confirm password: ");
            _passwdCheck = cin.nextLine();
        }

        System.out.println("Date of birth: ");
        String _dob = cin.nextLine();

        DateFormat format = new SimpleDateFormat("dd/mm/yyyy", Locale.GERMANY);
        Date _date = format.parse(_dob);

        User user = new User(_name, _email, _date,0.0,0);
        Credentials credentials = new Credentials(user.getId(), _passwd);

        users.add(user);
        creds.add(credentials);

    }

    public void addCard(User user){

        Scanner cin = new Scanner(System.in);
        System.out.println("Card number: ");
        String cardNumber = cin.nextLine();
        System.out.println("CVV: ");
        int cvv = Integer.parseInt(cin.nextLine());

        Card card = new Card(user.getId(), cardNumber, cvv);

        user.addCard(card);

    }
}

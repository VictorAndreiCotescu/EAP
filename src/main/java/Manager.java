import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Classes.Card;
import Classes.Credentials;
import Classes.User;
import RW.*;

public class Manager {


    public void system() throws ParseException, IOException {

        int userTryingToLogIn = startSession();
        if (userTryingToLogIn > 2) { // daca logIn returneaza corect
            session(userTryingToLogIn);
        }

    }

    public int startSession() throws ParseException, IOException {

        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.println("3. Exit");

        Scanner cin = new Scanner(System.in);
        switch (Integer.parseInt(cin.nextLine())) {

            case 1:
                return logIn(); //logIn returneaza userId int

            case 2:
                if (createUser()) // create user returneaza bool in functie de success/fail
                    startSession();
                return 1;

            case 3:
                return 0;

            default:
                startSession();
                break;
        }
        return 0;
    }

    public void session(int userId) {

        System.out.println();

    }

    public static String encrypt(String strToEncrypt, String SECRET_KEY, String SALT) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String SECRET_KEY, String SALT) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strEncrypted, String strKey) throws Exception {
        String strData;

        try {
            SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted = cipher.doFinal(strEncrypted.getBytes());
            strData = new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean createUser() throws ParseException, IOException {


        Scanner cin = new Scanner(System.in);
        System.out.print("Email: ");
        String _email = cin.nextLine();
        while (!isValidEmailAddress(_email)) {
            System.err.println("Email not ok. Try Again.");
            System.out.print("Email: ");
            _email = cin.nextLine();
        }

        System.out.print("Name: ");
        String _name = cin.nextLine();

        System.out.print("Set password: ");
        String _passwd = cin.nextLine();
        System.out.print("Confirm password: ");
        String _passwdCheck = cin.nextLine();
        while (!_passwd.equals(_passwdCheck)) {
            System.err.print("Password must match! Try Again.\n");
            System.out.print("Set password: ");
            _passwd = cin.nextLine();
            System.out.print("Confirm password: ");
            _passwdCheck = cin.nextLine();
        }

        //TODO check min 18
        System.out.print("Date of birth: ");
        String _dob = cin.nextLine();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date _date = format.parse(_dob);

        String strDate = format.format(_date);


        User user = new User(_name, _email, strDate, 0.0, 0);
        Credentials credentials = new Credentials(user.getId(), encrypt(_email, _passwd, _email + _passwd));


        CSVWriter csvWriter = CSVWriter.getInstance();

        try {
            //To accounts csv

            csvWriter.write(user);
            csvWriter.write(credentials);

            System.out.println("Account created successfully!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addCard(User user) {

        Scanner cin = new Scanner(System.in);

        System.out.print("Classes.Card number: ");
        String cardNumber = cin.nextLine();

        System.out.print("CVV: ");
        int cvv = Integer.parseInt(cin.nextLine());

        Card card = new Card(user.getId(), cardNumber, cvv);

        user.addCard(card);

    }

    public int logIn() {

        User user = new User();

        Scanner cin = new Scanner(System.in);

        System.out.print("Email: ");
        String _email = cin.nextLine();

        System.out.print("Password: ");
        String _passwd = cin.nextLine();

        boolean ok = false;

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/main/java/Accounts"));
            String line = reader.readLine();

            while (line != null) {

                String values[] = line.split("\\s+");

                /*System.out.println(decrypt(values[0], _passwd, _email + _passwd));
                System.out.println(decrypt(values[1], _email, _email + _passwd));*/

                if (Objects.equals(decrypt(values[0], _passwd, _email + _passwd), _email) && Objects.equals(decrypt(values[1], _email, _email + _passwd), _passwd))
                    ok = true;

                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        if (ok)
            return user.getId();
        else
            return -1;
    }

}

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

public class Manager {

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

    public void createUser(List<User> users, List<Credentials> creds) throws ParseException {


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
            System.err.println("Password must match! Try Again.");
            System.out.print("Set password: ");
            _passwd = cin.nextLine();
            System.out.print("Confirm password: ");
            _passwdCheck = cin.nextLine();
        }

        System.out.print("Date of birth: ");
        String _dob = cin.nextLine();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY);
        Date _date = format.parse(_dob);


        User user = new User(_name, _email, _date, 0.0, 0);
        Credentials credentials = new Credentials(user.getId(), _passwd);

        users.add(user);
        creds.add(credentials);

        File file = new File("Accounts");

        try {
            FileWriter writer = new FileWriter("Accounts");
            writer.write(encrypt(_email, _passwd, _email+_passwd) + " ");
            writer.write(encrypt(_passwd, _email,_email+_passwd));
            writer.close();
            System.out.println("Account created successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred. Please try again!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCard(User user) {

        Scanner cin = new Scanner(System.in);

        System.out.print("Card number: ");
        String cardNumber = cin.nextLine();

        System.out.print("CVV: ");
        int cvv = Integer.parseInt(cin.nextLine());

        Card card = new Card(user.getId(), cardNumber, cvv);

        user.addCard(card);

    }

    public User logIn() {

        User user = new User();

        Scanner cin = new Scanner(System.in);

        System.out.print("Email: ");
        String _email = cin.nextLine();

        System.out.print("Password: ");
        String _passwd = cin.nextLine();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Accounts"));
            String line = reader.readLine();

            while (line != null) {

                String values[] = line.split("\\s+");

                System.out.println(decrypt(values[0], _passwd, _email+_passwd));
                System.out.println(decrypt(values[1], _email,_email+_passwd));

                if (Objects.equals(decrypt(values[0], _passwd, _email + _passwd), _email) && Objects.equals(decrypt(values[1], _email, _email + _passwd), _passwd)) {
                        System.out.println("Successfully logged in!");
                }

                line = reader.readLine();
            }
            reader.close();
            System.out.println("Incorrect email or password! Try again.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}

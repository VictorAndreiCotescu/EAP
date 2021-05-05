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
import java.io.FileInputStream;
import java.io.IOException;

import Classes.*;
import RW.*;

public class Manager {

    private CSVReader reader = CSVReader.getInstance();
    private Logger logger = Logger.getInstance();

    private User user = new User();
    private Credentials creds = new Credentials();
    private Transaction transaction = new Transaction();
    private Auction auction = new Auction();

    private List <User> users = new ArrayList<>();
    private List <Credentials> credentials = new ArrayList<>();
    private List <Auction> auctions = new ArrayList<>();

    private File usersFile = new File ("src/main/java/accs.csv");
    private File credsFile = new File ("src/main/java/creds.csv");
    private File transactionsFile = new File ("src/main/java/transactions.csv");
    private File auctionsFile = new File ("src/main/java/auctions.csv");




    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    private static final Manager instance = new Manager();

    private Manager(){}

    public static Manager getInstance(){
        return instance;
    }


    public void system() throws ParseException, IOException {

        int userTryingToLogIn = startSession();
        if (userTryingToLogIn > 2) {// daca logIn returneaza corect
            clearScreen();
            session(userTryingToLogIn);
        }


    }

    public int startSession() throws ParseException, IOException {


        logger.log("login session started");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.println("3. Exit");

        Scanner cin = new Scanner(System.in);
        switch (Integer.parseInt(cin.nextLine())) {

            case 1:
                return logIn(); //logIn returneaza userId int

            case 2:
                if (createUser()) { // create user returneaza bool in functie de success/fail
                    clearScreen();
                    startSession();
                }
                return 1;

            case 3:
                clearScreen();
                return 0;

            default:
                logger.log("login failed by wrong user input");
                startSession();
                break;
        }
        return 0;
    }

    public void session(int userId) throws IOException {
        logger.log("user succesfully logged in");
        System.out.println("Bine ati venit in contul dvs.!");


        System.out.println("1. Active auctions"); //extendsclass.com/csv-generator.html for users also
        System.out.println("2. Rgister an object"); //add an obgect to auction
        System.out.println("3. Your account"); //1. balance 2. addCard 3. Transactions etc?
        System.out.println("4. Settings"); //
        System.out.println("5. Exit"); //TODO sign out only option

        Scanner cin = new Scanner(System.in);
        switch (Integer.parseInt(cin.nextLine())) {

            case 1:
                auctions = reader.read(auction,auctionsFile);
                for(int i = 0;  i < auctions.size(); ++i)
                    System.out.println(auctions.get(i).getId());
            case 2:

            case 3:

            case 4:

            case 5:
                logger.log("user finished session");
                clearScreen();
                yourAcc(userId);
                return;


            default:

        }

    }

    public void yourAcc(int userId) throws IOException {
        BufferedReader br = null;
        String[] parts = {""};
        try {

            br = new BufferedReader(new FileReader("src/main/java/accs.csv"));
            String sCurrentLine;


            sCurrentLine = br.readLine();
            String lastLine = sCurrentLine;
            parts = lastLine.split(",");
            while (Integer.parseInt(parts[0]) != userId) {
                sCurrentLine = br.readLine();
                lastLine = sCurrentLine;
                parts = lastLine.split(",");
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
        clearScreen();
        System.out.println("User: " + parts[1] + " ID: " + parts[0]  + '\n' + "Rank: " + parts[5]);
        System.out.println("Email:" + parts[2] + '\n');
        System.out.println("Balance: " + parts[4]);


        System.out.println("1. Add a card");
        System.out.println("2. Top up");
        System.out.println("3. Change password");
        System.out.println("4. Back");



        Scanner cin = new Scanner(System.in);
        switch (Integer.parseInt(cin.nextLine())) {

            case 1:
                /*clearScreen();
                logger.log("user wwants to add a card")
                addCard(userId);*/
            case 2:
               /* clearScreen();
                logger.log("user wants to top up a card");
                topUp(userId, idCard);*/ //more cards maybe?
            case 3:
               /* clearScreen();
                logger.log("user wants to change password");
                changePasswd(userId); */
            case 4:
                clearScreen();
                session(userId);

            default:

        }



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
        while (!isValidEmailAddress(_email)) { //TODO check if unique
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
            logger.log("account successfully created");
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

        users = reader.read(user, usersFile);
        credentials = reader.read(creds, credsFile);

        Scanner cin = new Scanner(System.in);

        System.out.print("Email: ");
        String _email = cin.nextLine();

        System.out.print("Password: ");
        String _passwd = cin.nextLine();

        /*System.out.println(decrypt(values[0], _passwd, _email + _passwd));
                System.out.println(decrypt(values[1], _email, _email + _passwd));*/

       /* System.out.println(credentials.get(0).getPasswd());

        System.out.println(decrypt(credentials.get(0).getPasswd(), _passwd, _email + _passwd));*/

        for (int i = 0; i < users.size(); ++i) {
            //System.out.println(users.get(i).getId() + users.get(i).getEmail());
            if (users.get(i).getEmail().equals(_email))
                if (decrypt(credentials.get(i).getPasswd(), _passwd, _email + _passwd).equals(_email)) {
                    //System.out.println(users.get(i).getId());
                    return users.get(i).getId();
                }
        }
        return -1;

    }

}

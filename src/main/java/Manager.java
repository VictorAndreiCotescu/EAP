import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import java.sql.Connection;

import Classes.*;
import RW.*;
import DBConn.*;
import Tools.*;
import Repositories.*;
import Menus.*;


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
    private Connection connection = DBConnection.connectDB();


    private static final Manager instance = new Manager();

    private Manager(){}

    public static Manager getInstance(){
        return instance;
    }


    public void system() throws ParseException, IOException, SQLException {

        userRepo.insertUserOnStart(connection); //add default admin user
        cardRepo.addDefaultCardToAdmin(connection); //add card to admin
        auctionRepo.insertDefaultAuction(connection); //add some default auctions

        int userTryingToLogIn = startSession(connection);
        if (userTryingToLogIn > 0) {// daca logIn returneaza corect
            Tools.clearScreen();
            session(userTryingToLogIn);
        } else {
            Tools.clearScreen();
            System.out.println("Email or passwd incorrect");
            system();
        }

    }

    public int startSession(Connection connection) throws ParseException, IOException, SQLException {

        return Menu.startSession(connection);

    }

    public void session(int userId) throws IOException, SQLException, ParseException {

            Menu.session(userId, connection);

    }


    public void getAuctions(Connection connection) throws SQLException {

        PreparedStatement stGetAuctions = connection.prepareStatement("SELECT * from auctions");

        ResultSet resultSet = stGetAuctions.executeQuery();

        while(resultSet.next()){

            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("object"));
            System.out.println(resultSet.getDate("start_date"));
            System.out.println(resultSet.getInt("minRank") + '\n');

        }
    }

    public void yourAcc(int userId, Connection connection) throws IOException, SQLException, ParseException {

        Menu.yourAcc(userId, connection);
    }


    public boolean createUser(Connection connection) throws ParseException, IOException, SQLException {

        //partea 2 csv
        /*CSVWriter csvWriter = CSVWriter.getInstance();

        try {
            csvWriter.write(user);
            csvWriter.write(credentials);

            System.out.println("Account created successfully!");
            logger.log("account successfully created");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //partea 3 local database
        return userRepo.insertUser();

    }

    public void addCard(User user) throws SQLException, IOException, ParseException {

        //partea 2 csv
        /*Scanner cin = new Scanner(System.in);

        System.out.print("Classes.Card number: ");
        String cardNumber = cin.nextLine();

        System.out.print("CVV: ");
        int cvv = Integer.parseInt(cin.nextLine());

        Card card = new Card(user.getId(), cardNumber, cvv);

        user.addCard(card);*/


        //partea 3 local database
        cardRepo.addCard(user.getId());

    }

    public int logIn(Connection connection) throws SQLException {

        return Menu.logIn(connection);

    }

}

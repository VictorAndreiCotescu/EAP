package Menus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Classes.*;
import Tools.*;
import Repositories.*;

public class Menu {

    private static Logger logger = Logger.getInstance();

    public static int startSession(Connection connection) throws SQLException, IOException, ParseException {
        logger.log("login session started");
        System.out.println("1. Log In");
        System.out.println("2. Register");
        System.out.println("3. Exit");

        Scanner cin = new Scanner(System.in);
        switch (Integer.parseInt(cin.nextLine())) {

            case 1:
                return logIn(connection); //logIn returneaza userId int

            case 2:
                if (userRepo.insertUser()) { // create user returneaza bool in functie de success/fail
                    Tools.clearScreen();
                    startSession(connection);
                } else {
                    logIn(connection);
                }
                //return 1; de ce

            case 3:
                connection.close();
                Tools.clearScreen();
                return 0;

            default:
                startSession(connection);
                break;
        }
        return 0;
    }

    public static void session(int userId, Connection connection) throws IOException, SQLException, ParseException {

        logger.log("user succesfully logged in");
        System.out.println("welcome");

        List<Auction> auctions = new ArrayList<>();
        String sqlGetAuctions = "SELECT * FROM auctions";
        PreparedStatement stGetAuctions = connection.prepareStatement(sqlGetAuctions);

        try{
            ResultSet resultSet = stGetAuctions.executeQuery();
            while(resultSet.next()){

                Auction auction = new Auction(  resultSet.getInt    (   "id"            ),
                                                resultSet.getString (   "object"        ),
                                                resultSet.getString (   "start_date"    ),
                                                resultSet.getInt    (   "minRank"       ));
                auctions.add(auction);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }


        System.out.println("1. Active auctions"); //extendsclass.com/csv-generator.html for users also
        System.out.println("2. Rgister an object"); //add an obgect to auction
        System.out.println("3. Your account"); //1. balance 2. addCard 3. Transactions etc?
        System.out.println("5. Exit"); //TODO sign out only option

        Scanner cin = new Scanner(System.in);
        switch (Integer.parseInt(cin.nextLine())) {

            case 1:
                //etapa 2 csv
                /*auctions = reader.read(auction, auctionsFile);
                for(int i = 0;  i < auctions.size(); ++i)
                    System.out.println(auctions.get(i).getId());*/

                //etapa 3 local database
                Tools.clearScreen();
                for(int i = 0; i < auctions.size(); ++i)
                    System.out.println(auctions.get(i));

            case 2:
                //timp?
            case 3:
                Tools.clearScreen();
                yourAcc(userId, connection);
            case 4:
                logger.log("user finished session");
                connection.close();
                Tools.clearScreen();
                return;


            default:

        }
    }

    public static void yourAcc(int userId, Connection connection) throws IOException, SQLException, ParseException {

        //partea 2 csv
        /*BufferedReader br = null;
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
        Tools.clearScreen();
        System.out.println("User: " + parts[1] + " ID: " + parts[0]  + '\n' + "Rank: " + parts[5]);
        System.out.println("Email:" + parts[2] + '\n');
        System.out.println("Balance: " + parts[4]);*/

        //partea 3 local database


        System.out.println("1. Add a card");
        System.out.println("2. Top up");
        System.out.println("3. Change password");
        System.out.println("4. Delete account");
        System.out.println("5. Back");



        Scanner cin = new Scanner(System.in);
        switch (Integer.parseInt(cin.nextLine())) {

            case 1:
                Tools.clearScreen();
                logger.log("user wwants to add a card");
                cardRepo.addCard(userId);
            case 2:
               /* clearScreen();
                logger.log("user wants to top up a card");
                topUp(userId, idCard);*/ //more cards maybe?
            case 3:
                Tools.clearScreen();
                logger.log("user wants to change password");
                if(userRepo.changePassword(userId, connection))
                    session(userId, connection);
                else
                    session(userId, connection);
            case 4:
                Tools.clearScreen();
                logger.log("user wants to delete account");
                if(userRepo.deleteUser(userId, connection))
                    startSession(connection);
                else
                    session(userId, connection);
            case 5:
                Tools.clearScreen();
                session(userId, connection);

        }
    }

    public static int logIn(Connection connection) throws SQLException {

        /*users = reader.read(user, usersFile);
        credentials = reader.read(creds, credsFile);*/

        Scanner cin = new Scanner(System.in);

        System.out.print("Email: ");
        String _email = cin.nextLine();

        System.out.print("Password: ");
        String _passwd = cin.nextLine();

        String pass = Tools.encrypt(_passwd, _email, _email + _passwd);
        System.out.println(pass);

        PreparedStatement stFindUser = connection.prepareStatement("SELECT id FROM user WHERE email = ?");
        PreparedStatement stCheckPasswd = connection.prepareStatement("SELECT passwd FROM credentials WHERE id_user = ?");

        System.out.println(pass);

        try {

            stFindUser.setString(1, _email);

            ResultSet resultSet = stFindUser.executeQuery();

            System.out.println("aici1");


            if(resultSet.next()) {
                try {
                    System.out.println("aici1");
                    System.out.println(resultSet.getInt("id"));
                    stCheckPasswd.setInt(1, resultSet.getInt("id_user"));
                    ResultSet resultSetPass = stCheckPasswd.executeQuery();
                    System.out.println("aici1");

                    if(resultSetPass.next()){
                        System.out.println(resultSetPass.getString("passwd"));
                        if (resultSetPass.getString("passwd").equals(pass))
                            return resultSet.getInt("id");
                        else
                            return -1;
                    }

                } catch (SQLException ex){
                    System.out.println("aici1");
                    return ex.getErrorCode();
                }
                System.out.println(resultSet.getInt("id"));
                return resultSet.getInt("id");
            }
            else
                return -1;

        } catch (SQLException ex) {
            System.out.println(ex + " aici");
        }

        /*System.out.println(decrypt(values[0], _passwd, _email + _passwd));
                System.out.println(decrypt(values[1], _email, _email + _passwd));*/

       /* System.out.println(credentials.get(0).getPasswd());

        System.out.println(decrypt(credentials.get(0).getPasswd(), _passwd, _email + _passwd));*/

        /*for (int i = 0; i < users.size(); ++i) {
            //System.out.println(users.get(i).getId() + users.get(i).getEmail());
            if (users.get(i).getEmail().equals(_email))
                if (decrypt(credentials.get(i).getPasswd(), _passwd, _email + _passwd).equals(_email)) {
                    //System.out.println(users.get(i).getId());
                    return users.get(i).getId();
                }
        }*/
        return -1;

    }

}

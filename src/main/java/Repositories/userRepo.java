package Repositories;

import Classes.*;
import DBConn.*;
import Tools.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class userRepo {

    private static Connection connection = DBConnection.connectDB();

    public userRepo userRepo;

    public static void insertUserOnStart() throws SQLException {

        String sqlUser = "INSERT INTO user (id, name, email, dob, balance, `rank`, cards, transaction_history) VALUES (?,?,?,?,?,?,?,?)";

        String sqlCredentials = "INSERT INTO credentials (id_user, passwd) VALUES (?,?)";

        PreparedStatement stAddUser = connection.prepareStatement(sqlUser);
        PreparedStatement stAddCreds = connection.prepareStatement(sqlCredentials);

        try {

            stAddUser.setInt(1, Tools.generateId(connection));
            stAddUser.setString(2, "admin");
            stAddUser.setString(3, "admin@auction.ro");
            stAddUser.setString(4, "03/12/1999");
            stAddUser.setDouble(5, 0.0);
            stAddUser.setInt(6, 0);
            stAddUser.setInt(7, 0);
            stAddUser.setString(8, "no transaction history");


            stAddCreds.setInt(1, Tools.generateId(connection));
            stAddCreds.setString(2, Tools.encrypt("adminadmin", "admin@auction.ro", "admin@auction.ro" + "adminadmin"));

            stAddCreds.executeUpdate();
            stAddUser.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex + " insertUserOnStart");
        }

    }

    public static boolean insertUser() throws SQLException, ParseException {

        Scanner cin = new Scanner(System.in);
        System.out.print("Email: ");
        String _email = cin.nextLine();

        while (!Tools.isValidEmailAddress(_email)) {
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

        System.out.print("Date of birth: ");
        String _dob = cin.nextLine();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date _date = format.parse(_dob);

        String strDate = format.format(_date);


        User user = new User(_name, _email, strDate, 0.0, 0);
        Credentials credentials = new Credentials(user.getId(), Tools.encrypt(_email, _passwd, _email + _passwd));
        String sqlUser = "INSERT INTO user (id, name, email, dob, balance, `rank`, cards, transaction_history) VALUES (?,?,?,?,?,?,?,?)";

        String sqlCredentials = "INSERT INTO credentials (id_user, passwd) VALUES (?,?)";

        PreparedStatement stAddUser = connection.prepareStatement(sqlUser);
        PreparedStatement stAddCreds = connection.prepareStatement(sqlCredentials);

        try {
            stAddUser.setInt    (1, user.getId());
            stAddUser.setString (2, user.getName());
            stAddUser.setString (3, user.getEmail());
            stAddUser.setString (4, user.getDob());
            stAddUser.setDouble (5, user.getBalance());
            stAddUser.setInt    (6, user.getRank());
            stAddUser.setInt    (7, 0);
            stAddUser.setString (8, "no transaction history");


            stAddCreds.setInt   (1, user.getId());
            stAddCreds.setString(2, Tools.encrypt(user.getEmail(), credentials.getPasswd(), user.getEmail() + credentials.getPasswd()));

            stAddCreds.executeUpdate();
            stAddUser.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }


}

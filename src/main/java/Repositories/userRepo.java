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



    public static void insertUserOnStart(Connection connection) throws SQLException {

        String sqlUser =        "INSERT INTO user (id, name, email, dob, balance, `rank`, cards, transaction_history) VALUES (?,?,?,?,?,?,?,?)";
        String sqlCredentials = "INSERT INTO credentials (id_user, passwd) VALUES (?,?)";

        PreparedStatement stAddUser = connection.prepareStatement(sqlUser);
        PreparedStatement stAddCreds = connection.prepareStatement(sqlCredentials);

        try {

            stAddUser.setInt    (1, 1);
            stAddUser.setString (2, "admin");
            stAddUser.setString (3, "admin@auction.ro");
            stAddUser.setString (4, "03/12/1999");
            stAddUser.setDouble (5, 0.0);
            stAddUser.setInt    (6, 0);
            stAddUser.setInt    (7, 0);
            stAddUser.setString (8, "no transaction history");


            stAddCreds.setInt   (1, 1);
            stAddCreds.setString(2, "adminadmin"); //cumva nu merge?

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

        String sqlUser =        "INSERT INTO user (id, name, email, dob, balance, `rank`, cards, transaction_history) VALUES (?,?,?,?,?,?,?,?)";
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
            stAddCreds.setString(2, _passwd);

            stAddCreds.executeUpdate();
            stAddUser.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public static boolean deleteUser(int userId, Connection connection) throws SQLException {


        Tools.clearScreen();

        Scanner cin = new Scanner(System.in);
        System.out.print("Are you sure you want to delete your account? (Y/N)");
        String confirmation = cin.nextLine();

        if(confirmation.equals('y') || confirmation.equals('Y')) {

            String sqlDeleteUser =              "DELETE FROM user WHERE id = ?";
            String sqlDeleteUserCredentials =   "DELETE FROM credentials WHERE id_user = ?";
            String sqlDeleteUserCards =         "DELETE FROM transaction_history WHERE id_user = ?";

            PreparedStatement stDeleteUser = connection.prepareStatement(sqlDeleteUser);
            PreparedStatement stDeleteUserCredentials = connection.prepareStatement(sqlDeleteUserCredentials);
            PreparedStatement stDeleteUserTransactionHistory = connection.prepareStatement(sqlDeleteUserCards);
            PreparedStatement stCommit = connection.prepareStatement("COMMIT");
            try {

                stDeleteUser.setInt(1, userId);
                stDeleteUserCredentials.setInt(1, userId);
                stDeleteUserTransactionHistory.setInt(1, userId);

                stDeleteUser.executeUpdate();
                stDeleteUserCredentials.executeUpdate();
                stDeleteUserTransactionHistory.executeUpdate();
                stCommit.executeQuery();

                return true;

            } catch (SQLException ex) {
                System.out.println(ex);
                return false;
            }
        } else {
            Tools.clearScreen();
            return false;
        }
    }

    public static boolean changePassword(int userId, Connection connection) throws SQLException {

        Tools.clearScreen();

        Scanner cin = new Scanner(System.in);
        System.out.print("New passwd: ");
        String passwd = cin.nextLine();
        System.out.print("Confirm new passwd: ");
        String passwdConfirm = cin.nextLine();


        if(passwd.equals(passwdConfirm)) {

            String sqlDeleteUser = "UPDATE credentials SET passwd = ? WHERE id_user = ?";

            PreparedStatement stUpdatePasswd = connection.prepareStatement(sqlDeleteUser);
            PreparedStatement stCommit = connection.prepareStatement("COMMIT");

            try {

                stUpdatePasswd.setString(1, passwd);
                stUpdatePasswd.setInt(2, userId);

                stUpdatePasswd.executeUpdate();
                stCommit.executeUpdate();

                return true;
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        } else {

            Tools.clearScreen();
            return false;

        }

        return false;

    }


}

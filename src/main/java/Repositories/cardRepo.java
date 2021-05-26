package Repositories;

import Classes.*;
import DBConn.DBConnection;
import Menus.Menu;
import Tools.Tools;
import com.mysql.cj.protocol.Resultset;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class cardRepo {

    private static Connection connection = DBConnection.connectDB();

    public static void addDefaultCardToAdmin(Connection connection) throws SQLException {

        String sqlCredentials = "INSERT INTO credentials (cvv) VALUES (?)";
        String sqlCard = "INSERT INTO user (cards) VALUES (?)";

        PreparedStatement stCredentials = connection.prepareStatement(sqlCredentials);
        PreparedStatement stCard = connection.prepareStatement(sqlCard);

        try {

            stCredentials.setInt(1, 001); //cvv
            stCard.setInt(1, 123456); //card number

            stCredentials.executeUpdate();
            stCard.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
        }


    }

    public static void addCard(int userId) throws SQLException, IOException, ParseException {

        Scanner cin = new Scanner(System.in);

        System.out.print("Classes.Card number: ");
        String cardNumber = cin.nextLine();

        System.out.print("CVV: ");
        int cvv = Integer.parseInt(cin.nextLine());

        String sqlGetUserId = "SELECT * FROM user WHERE id = ?";
        PreparedStatement stGetUserId = connection.prepareStatement(sqlGetUserId);


        try{

            stGetUserId.setInt(1, userId);
            ResultSet resultSet = stGetUserId.executeQuery();

            if (resultSet.next()){

                String sqlInsertCard =  "INSERT INTO user (cards) values (?)";
                String sqlAddCvv =      "INSERT INTO credentials (cvv) values (?)";

                PreparedStatement stInsertCard = connection.prepareStatement(sqlInsertCard);
                PreparedStatement stAddCvv = connection.prepareStatement(sqlAddCvv);

                try{
                    stInsertCard.setString(1, cardNumber);
                    stAddCvv.setInt(1, cvv);

                    stInsertCard.executeUpdate();
                    stAddCvv.executeUpdate();

                } catch (SQLException ex) {
                    System.out.println(ex);
                    Menu.yourAcc(userId, connection);
                }

            } else {
                System.out.println("No existing user with id" + userId);
                Menu.yourAcc(userId, connection);
            }


        } catch (SQLException | IOException | ParseException ex){
            System.out.println(ex);
            Menu.yourAcc(userId, connection);
        }

        Tools.clearScreen();
        System.out.println("Card added successfull!");
        Menu.yourAcc(userId, connection);

    }

}

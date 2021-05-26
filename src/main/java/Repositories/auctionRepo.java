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
public class auctionRepo {

    private static Connection connection = DBConnection.connectDB();

    public static void insertDefaultAuction(Connection connection) throws SQLException {
        String sqlAuction = "INSERT INTO auctions VALUES (?,?,?,?)";
        PreparedStatement stAddAuction = connection.prepareStatement(sqlAuction);

        try {

            stAddAuction.setInt     (1, 91000);
            stAddAuction.setString  (2, "car");
            stAddAuction.setString  (3, "26/05/2021");
            stAddAuction.setInt     (4, 0);

            stAddAuction.executeUpdate();

            stAddAuction.setInt     (1, 91001);
            stAddAuction.setString  (2, "bike");
            stAddAuction.setString  (3, "26/05/2021");
            stAddAuction.setInt     (4, 1);

            stAddAuction.executeUpdate();

            stAddAuction.setInt     (1, 91002);
            stAddAuction.setString  (2, "phone");
            stAddAuction.setString  (3, "26/05/2021");
            stAddAuction.setInt     (4, 1);


        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}

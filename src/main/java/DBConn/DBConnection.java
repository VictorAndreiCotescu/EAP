package DBConn;

import RW.CSVWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DBConnection {

    public DBConnection() {
    }

    public static Connection connectDB() {
        String url = "jdbc:mysql://localhost:3307/auction_app";

        try {
            Connection connection = DriverManager.getConnection(url, "root", "passwd");
            return connection;
        } catch (SQLException ex) {
            System.out.println("Conexiunea la baza de date a esuat!");
            ex.printStackTrace();
            return null;
        }
    }

}
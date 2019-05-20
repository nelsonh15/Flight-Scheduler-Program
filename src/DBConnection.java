
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nelson
 */
public class DBConnection {

    private static Connection connection;
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBNelsonnmh5395";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);

        }
        return connection;
    }

}

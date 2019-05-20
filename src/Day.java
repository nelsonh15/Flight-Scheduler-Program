
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelson
 */
public class Day {
    
    private static Connection connection;
    private static PreparedStatement selectAllDays;
    private static PreparedStatement InsertDays;
        
    public static ArrayList <Date> getDays() {
            
        ArrayList <Date> results3 = null;
        ResultSet resultSet3 = null; 
            
        try {
            connection = new DBConnection().getConnection();
            selectAllDays = connection.prepareStatement("SELECT * FROM DAY");
            resultSet3 = selectAllDays.executeQuery();
            
            results3 = new ArrayList <Date> ();
            
            while (resultSet3.next()) {
                results3.add(resultSet3.getDate("DATE"));
            }
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return results3;
    }
        
    public void addDay(String spinner) {
        //This is for final project
        try {
            connection = new DBConnection().getConnection();
            InsertDays = connection.prepareStatement("INSERT INTO DAY (DATE) VALUES (?)");
            InsertDays.setString(1, spinner);
            InsertDays.executeUpdate();
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
}

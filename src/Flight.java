
import java.sql.Connection;
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
public class Flight {
    private static PreparedStatement selectAllFlights;
    private static PreparedStatement InsertCustomers;
    private static PreparedStatement RemoveFlightsANDSeats;
    private static Connection connection;
    private static PreparedStatement getCountSeats;
    
    public void addFlightsANDSeats(String textfield, String textfield1) {
        try {
            connection = new DBConnection().getConnection();
            InsertCustomers = connection.prepareStatement("INSERT INTO FLIGHT (NAME, SEATS)" + "VALUES (?, ?)");
            
            InsertCustomers.setString(1, textfield.toString());
            InsertCustomers.setString(2, textfield1.toString());
            InsertCustomers.executeUpdate();
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public void removeFlightsANDSeats(Object Flight) {
        try {
            connection = new DBConnection().getConnection();
            RemoveFlightsANDSeats = connection.prepareStatement("DELETE FROM FLIGHT WHERE NAME = ?");
            RemoveFlightsANDSeats.setString(1, Flight.toString());
            RemoveFlightsANDSeats.executeUpdate();
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public static ArrayList <String> getAllFlights() {
        ArrayList <String> results1 = null;
        ResultSet resultSet1 = null;
            
        try {
            connection = DBConnection.getConnection();
            selectAllFlights = connection.prepareStatement("SELECT * FROM FLIGHT");

            resultSet1 = selectAllFlights.executeQuery();
            results1 = new ArrayList <String> ();
            
        while (resultSet1.next()) {
            results1.add(resultSet1.getString("NAME")); 
            }
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return results1;
    }
        
    public static int getSeats (String flight) {
        int seats = 0;
        ResultSet resultSet2 = null;
        
        try {
            connection = DBConnection.getConnection();
            getCountSeats = connection.prepareStatement("SELECT SEATS FROM FLIGHT WHERE NAME = ?");

            getCountSeats.setString(1, flight);
            resultSet2 = getCountSeats.executeQuery();
            
        resultSet2.next();
        seats = resultSet2.getInt(1);
        }
           
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seats;
    }
}

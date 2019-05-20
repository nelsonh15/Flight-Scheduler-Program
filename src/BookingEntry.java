
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelson
 */
public class BookingEntry {
    
    private static Connection connection;
    private static PreparedStatement InsertBooking;
    private static PreparedStatement RemoveBooking;
    private static PreparedStatement getFlightSeats;
    private static PreparedStatement BookingsOnStatus;
    private static PreparedStatement BookingsOnStatus1;
    private static PreparedStatement getCustomerDayFlight;
    private static PreparedStatement get_customer_and_day;
    private static ResultSet resultSet;
    private static ResultSet resultSet1;
    private static ResultSet resultSet2;
    private static ResultSet resultSet3;
        
    /**
     *
     * @param Flight
     * @param Customer
     * @param date
     */
    public void addBooking(Object Flight, Object Customer, Object date) {
        try {
            connection = DBConnection.getConnection();
            InsertBooking = connection.prepareStatement("INSERT INTO BOOKINGS (CUSTOMER, FLIGHT, DAY, TIMESTAMP) "
                    + " VALUES (?, ?, ?, ?)");
                
            InsertBooking.setString(1, Customer.toString());
            InsertBooking.setString(2, Flight.toString());
            InsertBooking.setDate(3, Date.valueOf(date.toString()));
                
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            InsertBooking.setTimestamp(4, currentTimestamp);
            InsertBooking.executeUpdate();
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public void removeBooking(Object customer, Object date) {
        try {
            connection = DBConnection.getConnection();
            RemoveBooking = connection.prepareStatement("DELETE FROM BOOKINGS WHERE CUSTOMER = ? AND DAY = ?");
            RemoveBooking.setString(1, customer.toString());
            RemoveBooking.setString(2, date.toString());
            RemoveBooking.executeUpdate();
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
        
    public static ArrayList <String> getBookingStatus(String flight_combo_box, Date Date_combo_box) {
        ArrayList <String> status_customers = new ArrayList<String>();
            
        try {
            connection = DBConnection.getConnection();
            BookingsOnStatus = connection.prepareStatement("SELECT CUSTOMER FROM BOOKINGS WHERE FLIGHT = ? and DAY = ?");
            BookingsOnStatus.setString(1, flight_combo_box);
            BookingsOnStatus.setDate(2, Date_combo_box);
            resultSet = BookingsOnStatus.executeQuery();
        
            while (resultSet.next()) {
            status_customers.add(resultSet.getString("CUSTOMER"));
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }     
    return status_customers;
    }
    
    public static ArrayList <String> getCustomerFlightStatus(String customer_combo_box) {
        ArrayList <String> flight_status = new ArrayList<String>();
        
        try {
            connection = DBConnection.getConnection();
            BookingsOnStatus1 = connection.prepareStatement("SELECT FLIGHT FROM BOOKINGS WHERE CUSTOMER = ?");
            BookingsOnStatus1.setString(1, customer_combo_box);
            resultSet1 = BookingsOnStatus1.executeQuery();
            
            while (resultSet1.next()) {
                flight_status.add(resultSet1.getString("FLIGHT"));
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    return flight_status;
    }
    
    public static ArrayList <String> getCustomerDateStatus(String customer_combo_box) {
        ArrayList <String> date_status = new ArrayList<String>();
        try {
            connection = DBConnection.getConnection();
            BookingsOnStatus1 = connection.prepareStatement("SELECT DAY FROM BOOKINGS WHERE CUSTOMER = ?");
            BookingsOnStatus1.setString(1, customer_combo_box);
            resultSet2 = BookingsOnStatus1.executeQuery();
            
            while (resultSet2.next()) {
                date_status.add(resultSet2.getString("DAY"));
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    return date_status;
        
    }
        
    public static int getFlightSeats(String FLIGHT, Date DAY) {
        ResultSet resultSet = null;
        int seatsBooked = 0;
            
        try {
            connection = DBConnection.getConnection();
            getFlightSeats = connection.prepareStatement("SELECT count(FLIGHT) FROM BOOKINGS WHERE FLIGHT = ? and DAY = ?"); 
            getFlightSeats.setString(1, FLIGHT); 
            getFlightSeats.setDate(2, DAY); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            seatsBooked = resultSet.getInt(1);
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return seatsBooked; 
    }
    
    public static String getCustomerDayFlight(Object customer, Object day) {
        String getFlight = "";
        ResultSet resultSet3 = null;
        
        try {
            connection = DBConnection.getConnection();
            getCustomerDayFlight = connection.prepareStatement("SELECT FLIGHT FROM BOOKINGS WHERE CUSTOMER = ? AND DAY = ?");
            getCustomerDayFlight.setString(1, customer.toString());
            getCustomerDayFlight.setString(2, day.toString());
            resultSet3 = getCustomerDayFlight.executeQuery();
            
            //getFlight = new ArrayList <String> ();
            
            if (resultSet3.next()) {
                getFlight = resultSet3.getString("FLIGHT");
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    return getFlight;
    }
    
    public static List<List<String>> getCustomerANDDayWithFlight(Object flight) { // select customer and day where flight = ?
        List <String> customers = new ArrayList<String>();
        List <String> days = new ArrayList<String>();
        
        try {
            connection = DBConnection.getConnection();
            get_customer_and_day = connection.prepareStatement("SELECT CUSTOMER, DAY FROM BOOKINGS WHERE FLIGHT = ? ORDER BY TIMESTAMP");
            get_customer_and_day.setString(1, flight.toString());
            resultSet3 = get_customer_and_day.executeQuery();
            
            while (resultSet3.next()) {
                customers.add(resultSet3.getString("CUSTOMER"));
                days.add(resultSet3.getString("DAY"));
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        List<List<String>> result = new ArrayList<List<String>>();
        result.add(customers);
        result.add(days);
        
        return result;
    }
}

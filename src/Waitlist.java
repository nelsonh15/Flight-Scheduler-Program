

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
public class Waitlist {
    private static Connection connection;
    private static PreparedStatement InsertWaitlist;
    private static PreparedStatement RemoveWaitlist;
    private static PreparedStatement BookingsOnWaitlist;
    private static PreparedStatement Flight_waitlist;
    private static PreparedStatement Day_waitlist;
    private static PreparedStatement getWaitlistCustomer;
    private static PreparedStatement get_customer_and_day;
    private static ResultSet resultSet;
    private static ResultSet resultSet1;
    private static ResultSet resultSet2;
    private static ResultSet resultSet3;
    private static ResultSet resultSet4;
        
    public void addWaitList(Object Flight, Object Customer, Object date) {
        try {
            connection = new DBConnection().getConnection();
            InsertWaitlist = connection.prepareStatement("INSERT INTO WAITLIST (CUSTOMER, FLIGHT, DAY, TIMESTAMP) "
                    + " VALUES (?, ?, ?, ?)");
                
            InsertWaitlist.setString(1, Customer.toString());
            InsertWaitlist.setString(2, Flight.toString());
            InsertWaitlist.setDate(3, Date.valueOf(date.toString()));
                
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            InsertWaitlist.setTimestamp(4, currentTimestamp);
            InsertWaitlist.executeUpdate();

            }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
            }
        }
    
    public void removeWaitlist(Object Customer, Object date) {
        try {
            connection = new DBConnection().getConnection();
            RemoveWaitlist = connection.prepareStatement("DELETE FROM WAITLIST WHERE CUSTOMER = ? AND DAY = ?");
            RemoveWaitlist.setString(1, Customer.toString());
            RemoveWaitlist.setString(2, date.toString());
            RemoveWaitlist.executeUpdate();
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    
    }
        
    public static ArrayList <String> getWaitlist(Date waitlist_combo_box) { //get customer & flight given day
            
        ArrayList <String> waitlist_customers = new ArrayList<String>();
            
        try {
            connection = new DBConnection().getConnection();
            BookingsOnWaitlist = connection.prepareStatement("SELECT CUSTOMER, FLIGHT FROM WAITLIST WHERE DAY = ?");
            BookingsOnWaitlist.setDate(1, waitlist_combo_box);
            resultSet = BookingsOnWaitlist.executeQuery();
                
            while (resultSet.next()) {
                waitlist_customers.add(resultSet.getString("CUSTOMER"));
                waitlist_customers.add(resultSet.getString("FLIGHT"));
                }
            }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
            }
    return waitlist_customers;
    }
    
    public static String getWaitListCustomer(Object flight, Object day) { //get customer given flight & day
        String get_customer_waitlist = "";
        
        try {
            connection = new DBConnection().getConnection();
            getWaitlistCustomer = connection.prepareStatement("SELECT CUSTOMER FROM WAITLIST WHERE FLIGHT = ? AND DAY = ? ORDER BY TIMESTAMP");
            getWaitlistCustomer.setString(1, flight.toString());
            getWaitlistCustomer.setDate(2, Date.valueOf(day.toString()));
            resultSet3 = getWaitlistCustomer.executeQuery();
            
            if (resultSet3.next()) {
                get_customer_waitlist = resultSet3.getString("CUSTOMER");
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return get_customer_waitlist;
    }
    
    public static ArrayList <String> getWaitlistFlight(String customer_combo_box) {
        ArrayList <String> flight_waitlist = new ArrayList<String>();
        
        try {
            connection = new DBConnection().getConnection();
            Flight_waitlist = connection.prepareStatement("SELECT FLIGHT FROM WAITLIST WHERE CUSTOMER = ?");
            Flight_waitlist.setString(1, customer_combo_box);
            resultSet1 = Flight_waitlist.executeQuery();
            
            while (resultSet1.next()) {
                flight_waitlist.add(resultSet1.getString("FLIGHT"));
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    return flight_waitlist;
    }
    
    public static ArrayList <String> getWaitlistDay(String customer_combo_box) {
        ArrayList <String> day_waitlist = new ArrayList<String>();
        
        try {
            connection = new DBConnection().getConnection();
            Day_waitlist = connection.prepareStatement("SELECT DAY FROM WAITLIST WHERE CUSTOMER = ?");
            Day_waitlist.setString(1, customer_combo_box);
            resultSet2 = Day_waitlist.executeQuery();
            
            while (resultSet2.next()) {
                day_waitlist.add(resultSet2.getString("DAY"));
            }
        }
        
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    return day_waitlist;
    }
    
    public static List<List <String>> getCustomerANDDayWithFlight(Object flight) { // select customer and day where flight = ?
        List <String> customers = new ArrayList<String>();
        List <String> days = new ArrayList<String>();
        
        try {
            connection = new DBConnection().getConnection();
            get_customer_and_day = connection.prepareStatement("SELECT CUSTOMER, DAY FROM WAITLIST WHERE FLIGHT = ? ORDER BY TIMESTAMP");
            get_customer_and_day.setString(1, flight.toString());
            resultSet4 = get_customer_and_day.executeQuery();
            
            while (resultSet4.next()) {
                customers.add(resultSet4.getString("CUSTOMER"));
                days.add(resultSet4.getString("DAY"));
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
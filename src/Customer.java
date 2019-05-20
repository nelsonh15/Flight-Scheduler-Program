
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nelson
 */
public class Customer {
    private static PreparedStatement InsertCustomers;
    private static PreparedStatement DeleteCustomers;
    private static Connection connection;
    private static PreparedStatement selectAllPeople;
        
    
    public static ArrayList <String> getCustomer() {
        ArrayList <String> results2 = null;
        ResultSet resultSet2 = null;
        
        try {
            connection = DBConnection.getConnection();
            selectAllPeople = connection.prepareStatement("SELECT * FROM CUSTOMER");
            resultSet2 = selectAllPeople.executeQuery();
            
            results2 = new ArrayList <String> ();
                
            while (resultSet2.next()) {
                results2.add(resultSet2.getString("NAME"));
            }
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);      
        }
        return results2;
    }
    
    public void addCustomer(String textfield) {
        try {
            connection = new DBConnection().getConnection();
            InsertCustomers = connection.prepareStatement("INSERT INTO CUSTOMER (NAME) VALUES (?)");
            InsertCustomers.setString(1, textfield);
            InsertCustomers.executeUpdate();
            
        }
            
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
}


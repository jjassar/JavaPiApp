/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.jes.samples.hellostorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import static com.oracle.jes.samples.hellostorage.Myconstants.*;

/**
 *
 * @author pi
 */
public class StoreData {
    
      public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
  
   
    
    public boolean storelocal(String vel, String ser)
    {
        
        boolean res = false;
            Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
   try {
            // Load the embedded derby driver
            Class.forName(DB_DRIVER);
            // Open a connection to the database (create if it does not exist)
            connection = DriverManager.getConnection(DB_URL + ";create=true", null);
            // Greet
            System.out.println(ANSI_BLUE+ "Saving in  JavaDB!");
            // Create a SQL statement
            statement = connection.createStatement();
            try {
                // create the history table
                statement.execute(DB_TABLE);
            } catch (SQLException ignored) {
                // perhaps table already exists
            }
            
           
            // Record the timestamp 
         //statement.execute("INSERT INTO history(time, velocity) VALUES ('"
         //           + new Timestamp(System.currentTimeMillis()) +"," + vel + "')");
         
         statement.execute("INSERT INTO history(time, velocity) VALUES ('"
                    + new Timestamp(System.currentTimeMillis()) + "','"+vel+"')");
         
      
         
         
         System.out.println(ANSI_GREEN + "Saved     Locally!");
         
           
           res = true;
          
 
           
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Please put derby.jar in the classpath");
            System.exit(1);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            // Cleanup
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        // shutdown the database
        try {
            DriverManager.getConnection(DB_URL + ";shutdown=true");
        } catch (SQLException se) {
            if (!se.getSQLState().equals("08006")) {
                se.printStackTrace();
            }
            // otherwise ignore it : this is expected 
        }
        
        
        return res;
    }
    
    
    
    
}

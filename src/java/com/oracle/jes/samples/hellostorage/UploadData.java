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
import com.pi4j.system.SystemInfo;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pi
 */
public class UploadData {
    
    
      public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    

    String serno;
    
    public UploadData() {
        
         try {
                // Query & print history
                serno = SystemInfo.getSerial();
            } catch (IOException ex) {
                Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    
    
    
    
    public String upload()
    {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        ResultSet rs = null;
        
        String res="";
        
        
        
        
        
        SendToServlet srvSender = new SendToServlet();
         StringBuilder sb = new StringBuilder();
        try {
            // Load the embedded derby driver
            Class.forName(DB_DRIVER);
            // Open a connection to the database (create if it does not exist)
            connection = DriverManager.getConnection(DB_URL + ";create=true", null);
            // Greet
            System.out.println(ANSI_GREEN+ "Fetching   JavaDB!");
            // Create a SQL statement
            statement = connection.createStatement();
      
            rs = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM history");
            rs.next();
            int rcount = rs.getInt("rowcount");
            rs.close();
 
            
            if(rcount > 0)
            {
                
             resultset = statement.executeQuery("SELECT id, time, velocity FROM history ORDER BY id");
                
                System.out.println(ANSI_BLUE + "Uploading "+ rcount + " Records"); 
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            while (resultset.next()) {
                
                sb.append(serno);
                sb.append(",");
                
                sb.append(resultset.getString(3));
                sb.append(",");
                Timestamp t = resultset.getTimestamp(2);
            /* 
                Calendar cal1 = Calendar.getInstance();
                
                cal1.setTimeInMillis(t.getTime());
                
                DateFormat dfm = DateFormat.getDateInstance();
                dfm.format(cal1.getTime());
            */                
                
                sb.append(t.toString());
                sb.append("!");
                
                //upload data in database
                /*
                System.out.println("\t Uploading#" + resultset.getInt(1) + " @ "
                        + resultset.getTimestamp(2));
                    */
            }
            
            statement.execute("delete from history");
            res = "Upload    Complete";  
          //  System.out.println("Upload    Complete");
           //     System.out.println("");
                
            }
            else
            {
                res = "Zero      Records";
               // System.out.println("Zero      Records");
            }
            
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
        
       
        srvSender.sendRecord(sb.toString(), "RecvRecords");
       
        
        
        
        return res;
    }

    
    
    
    
    
    
}

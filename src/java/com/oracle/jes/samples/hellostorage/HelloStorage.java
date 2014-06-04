/*
 * Copyright (c) 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
package com.oracle.jes.samples.hellostorage;

import com.pi4j.system.SystemInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.net.www.http.HttpClient;

/**
 * This is a simple program that demonstrates how to persist data using JavaDB
 *
 * Each time this program is run, it records the timestamp in a 'history' table
 * within a 'hello' database
 */
public class HelloStorage{

    private  static httpcPoster1 post1;
    
    static boolean isLogin = false;
    
    
    
    private final static String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private final static String DB_NAME = "chronydata";
    private final static String DB_URL = String.format("jdbc:derby:%s", DB_NAME);
    
    StoreData sd = new StoreData();
    SendToServlet srvSend = new SendToServlet();
    FindMac fm = new FindMac();
    public void store(String vel,String ser) {
    
        int i=0;
        boolean netconnect = CheckInternet.testInet("192.168.1.5");
        
        if(netconnect)
        {
          
           String ts = (new Timestamp(System.currentTimeMillis())).toString();
           StringBuilder sb = new StringBuilder();
                sb.append(ser);
                sb.append(",");
                
                sb.append(vel);
                sb.append(",");

                sb.append(ts);
                sb.append("!");
            String r = srvSend.sendRecord(sb.toString(), "RecvRecords");
            System.out.println("St:" + r);
            
            
        }
        
        else
        {
     
        
        
        sd.storelocal(vel, ser);  
        System.out.println("St:Local ");    
      
        }
        
        if(netconnect)
        {
        if(!isLogin)
        {
        post1 = new httpcPoster1();
        post1.putMessage(1,vel,ser);
        isLogin = true;
        }
        if(isLogin)
        {
            i++;
            post1.putMessage(2,vel,ser);
         //   System.out.println("Live    Posted");
        }

        }
   
        
        
        
       
        
        
    }
    
 
    



    
    
    
}

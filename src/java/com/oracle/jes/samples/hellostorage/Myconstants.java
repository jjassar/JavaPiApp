/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.jes.samples.hellostorage;

/**
 *
 * @author pi
 */
public interface Myconstants {
    
    
       final static String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
       final static String DB_NAME = "chronydata";
       final static String DB_URL = String.format("jdbc:derby:%s", DB_NAME);
       final static String DB_TABLE = "CREATE TABLE history (id INTEGER NOT NULL GENERATED "
                        + "ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "time TIMESTAMP NOT NULL,"
                        + " velocity VARCHAR(50) NOT NULL)";
    
}

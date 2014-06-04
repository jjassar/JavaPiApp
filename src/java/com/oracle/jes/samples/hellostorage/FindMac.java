/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.jes.samples.hellostorage;


import com.pi4j.system.SystemInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

 
public class FindMac{
 
   public String getAddr()
   {
       String m= "";
       byte[] mac= {'0'};
 
	InetAddress ip;
	try {
 
		ip = InetAddress.getLocalHost();
	
                NetworkInterface network = NetworkInterface.getByName("eth0");
 
                
		 mac = network.getHardwareAddress();
 
		
                
                
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));		
		}
		m=sb.toString();
 
	} catch (UnknownHostException e) {
 
		e.printStackTrace();
 
	} catch (SocketException e){
 
		e.printStackTrace();
 
	}
        
     
       return m;
 
   }
   
   public String getSeial()
   {
       String serno="";
        try {
                 serno = SystemInfo.getSerial();
            } catch (IOException ex) {
                Logger.getLogger(HelloStorage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(HelloStorage.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       return serno;
   }
 
}
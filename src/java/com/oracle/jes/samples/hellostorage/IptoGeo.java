/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.jes.samples.hellostorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author pi
 */
public class IptoGeo {
    
    
    public static String getCurrentIPAddress2() throws MalformedURLException, IOException {
		
        
        HttpURLConnection conn = (HttpURLConnection)(new URL("http://ifconfig.me/ip").openConnection());
	
      //1  HttpClient httpclient = HttpClientBuilder.create().build();
      //1	       HttpGet g1 = new HttpGet("http://ifconfig.me/ip");
      //1            HttpResponse res = httpclient.execute(g1);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
  
        //1BufferedReader reader = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
	
   
               String response = reader.readLine();
               
		if (conn.getResponseCode() == 200) {
			return response.trim();
		} else {
			return null;
		}
         //1      return response;
	}
    
    
    
    
    
}

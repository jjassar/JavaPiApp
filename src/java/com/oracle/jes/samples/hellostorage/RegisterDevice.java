/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.jes.samples.hellostorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.json.simple.JSONObject;

/**
 *
 * @author pi
 */
public class RegisterDevice {
    
    
    HttpResponse response;
	
	String ipadd;
	//  JSONObject jsobj;
	
	String ip,port,url;
	boolean mode;
	
	
	public RegisterDevice()
	{
	
	
	
	}
	
	
	
	public String sendRecord(String m)
	{
		mode = false;
            String out=null;
            
	//	DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpClient httpclient = HttpClientBuilder.create().build();
        
		HttpPost httppostreq;
		if(!mode)
		{
		
		httppostreq = new HttpPost("http://192.168.1.5:8080/pichrony/RegNewChrony");
                
		
		}
		else
		{
			httppostreq = new HttpPost("http://security.netmaxjava.com/phlogin");
                        
		}
	
		
		StringEntity se=null;
		
       
        try {
            se = new StringEntity(m);
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(RegisterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
            
   
		
		
	//	se.setContentType("application/json;");
		
		
		httppostreq.setEntity(se);
		
			
		
	
		try {
		    HttpResponse respo = httpclient.execute(httppostreq);
		    if(respo != null) {
		        out = "";
		        InputStream inputstream = respo.getEntity().getContent();
		        out = convertStreamToString(inputstream);
		     
		        
		    } else {
		       
		    }
		} catch (ClientProtocolException e) {
		 
		} catch (IOException | IllegalStateException e) {
		}
		return out;
	}
	
                
                
	
	

	
	
	// ---------  Convert Stream data to String  ------------------	
		private String convertStreamToString(InputStream is) {
		    String line;
		    StringBuilder total = new StringBuilder();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    try {
		        while ((line = rd.readLine()) != null) {
		            total.append(line);
		        }
		    } catch (IOException e) {
		    }
		    return total.toString();
		}
	
	

		
		
	
    
    
    
}

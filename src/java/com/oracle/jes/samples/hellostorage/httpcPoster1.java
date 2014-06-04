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
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author pi
 */
public class httpcPoster1 {
	public String putMessage(int i,String vel,String ser)
	{
            
            
            
		String out=null;
                
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		
        HttpPost httppostreq = new HttpPost("http://192.168.1.5:8080/async-request-war/AjaxCometServlet");
	//HttpPost httppostreq = new HttpPost("http://192.168.1.5:8080/pichrony/AjaxCometServlet");
	
		
		//HttpPost httppostreq = new HttpPost("http://192.168.1.5:8080/async-request-war/AjServlet");
	
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                
                if(i==1)
                {
                pairs.add(new BasicNameValuePair("action", "login"));
		pairs.add(new BasicNameValuePair("name", ser)); 
                }
                else
                {
                    FindMac fm = new FindMac();
            
                    String m = fm.getAddr();
            
                pairs.add(new BasicNameValuePair("action", "post"));
		pairs.add(new BasicNameValuePair("name", ser));
		pairs.add(new BasicNameValuePair("message", vel));
		  
                }
		
		try {
			httppostreq.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		/*
		StringEntity se=null;
		try {
			se = new StringEntity(jsono.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		se.setContentType("application/json;charset=UTF-8");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
		httppostreq.setEntity(se);
		*/
		
		
		
		//HttpGet httpget = new HttpGet("http://192.168.1.5:8080/async-request-war/AjServlet");

	
		try {
		    HttpResponse response = httpclient.execute(httppostreq);
		    
		    if(response != null) {
		        out = "";
		        InputStream inputstream = response.getEntity().getContent();
		        out = convertStreamToString(inputstream);
		     
		        
		    } else {
		       
		    }
		} catch (ClientProtocolException e) {
		 
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return out;
	}
	
	// ---------  Convert Stream data to String  ------------------	
	private String convertStreamToString(InputStream is) {
	    String line = "";
	    StringBuilder total = new StringBuilder();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    try {
	        while ((line = rd.readLine()) != null) {
	            total.append(line);
	        }
	    } catch (Exception e) {
	    }
	    return total.toString();
	}

  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.jes.samples.hellostorage;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPortException;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This example code demonstrates how to perform serial communications using the
 * Raspberry Pi.
 *
 * @author Robert Savage
 */
public class SerialCommn {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    
    
    
    final static HelloStorage hs = new HelloStorage();
    final static FindMac fm = new FindMac();

    public static void main(String args[]) throws InterruptedException {
       
        // !! ATTENTION !!
        // By default, the serial port is configured as a console port 
        // for interacting with the Linux OS shell.  If you want to use 
        // the serial port in a software program, you must disable the 
        // OS from using this port.  Please see this blog article by  
        // Clayton Smith for step-by-step instructions on how to disable 
        // the OS console for this port:
        // http://www.irrational.net/2012/04/19/using-the-raspberry-pis-serial-port/
       
        
        boolean conn=false;
        
        
        
        /*
        System.out.println("Start");
        System.out.println("Menu");
        System.out.println("1:Results");
        
        System.out.println("2:Register");
        
        System.out.println("3:Test"); 
        */
        System.out.println(ANSI_GREEN +"System    Ready");
        
        
        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();
        
        // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput inet_pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
      
          // provision gpio pin #01 as an output pin and turn on
        final GpioPinDigitalOutput run_pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "LEDrun", PinState.LOW);
      
        
        
         // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput exitButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, PinPullResistance.PULL_DOWN);

         // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

         // provision gpio pin #00 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_DOWN);
   
         // provision gpio pin #03 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_DOWN);
   
        
            // create and register gpio pin listener
        myButton2.addListener(new GpioPinListenerDigital() {

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
         
                
             
                
                
                 if(event.getState() == PinState.LOW)
                 {
                
                     
                    System.out.println(ANSI_WHITE +"Velocity  Test Start" + ANSI_GREEN);
                          try {
                              Thread.sleep(1000);
                          } catch (InterruptedException ex) {
                              Logger.getLogger(SerialCommn.class.getName()).log(Level.SEVERE, null, ex);
                          }
                    
                   
                    
                    
                   //  System.out.println("Velocity");
                     hs.store("Test",fm.getSeial());
                        System.out.println(ANSI_WHITE + "000.000mps" + ANSI_GREEN);
                }
                    
                 }
            
           
            
        });
        
        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            
            
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                long start=0,stop=0;
                 
                
               if(event.getState() == PinState.LOW)
               {
                
               if(CheckInternet.testInet("192.168.1.5"))
                {
                 
                  String m = fm.getAddr();
                   String ser= fm.getSeial();
                   //System.out.println(ser);
                   //System.out.println(m);
                   System.out.println(ANSI_PURPLE +"Register  Online");
                   
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(SerialCommn.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   
                   //System.out.println("Online...");
                  
                   RegisterDevice reg = new RegisterDevice();
                    String s = reg.sendRecord(m +","+ ser);
                   if(s.equals("false"))
                   {
                       System.out.println(ANSI_BLUE +"Device    Registered" + ANSI_GREEN);
                      // System.out.println("Registered");
                       
                   }
                   else
                   {
                      
                       System.out.println(ANSI_RED +"Already   Registered" + ANSI_GREEN);
                       
                      // System.out.println("Registered");
                   
                   }
               //    System.out.println("");

                }
                    else
                    {
                         System.out.println(ANSI_RED +"Network    Failure");
       
                    }
               }
                
                
                
            }

       
            
        });
        
           
        
        
            // create and register gpio pin listener
        myButton3.addListener(new GpioPinListenerDigital() {

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

          if(event.getState() == PinState.LOW)            
          {
              
               if(CheckInternet.testInet("192.168.1.5"))
                {
              
                    System.out.println(ANSI_WHITE + "Trying    Upload" );
                    
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException ex) {
                       Logger.getLogger(SerialCommn.class.getName()).log(Level.SEVERE, null, ex);
                   }
                
                  String r =  new UploadData().upload();
                  
                     System.out.println(ANSI_GREEN + r);
                     
                 }
               else
               {
                    System.out.println(ANSI_RED +"Network    Failure");
               }
          }           
            }
            
        });
        
        exitButton.addListener(new GpioPinListenerDigital() {

            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
                
           
            System.exit(0);
            
            
            }
        });
        
        
        

        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

        // create and register the serial data listener
        serial.addListener(new SerialDataListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                // print out the data received to the console
                String v = event.getData();
                //System.out.print(v);
                
                
                
                hs.store(v,fm.getSeial());
                System.out.print(ANSI_WHITE + v + " m/s" +ANSI_GREEN);
                
                

            }
        });

        run_pin.high();
        
        try {
            // open the default serial port provided on the GPIO header
            serial.open(Serial.DEFAULT_COM_PORT, 38400);

            // continuous loop to keep the program running until the user terminates the program
            for (;;) {
          
                
                
                
                if(CheckInternet.testInet("192.168.1.5"))
                {
                    inet_pin.high();
                    conn = true;
                    // System.out.println(ANSI_GREEN +"System    Ready");
       
                }
                else
                {
                    inet_pin.low();
                    conn = false;
                   // System.out.println(ANSI_RED +"Internet    Failure");
              
                }
                    
                
                

                // wait 1 second before continuing
                Thread.sleep(10000);
            }
            
          

        } catch (SerialPortException ex) {
            System.out.println("SERIAL FAILED : " + ex.getMessage());
            return;
        }
    }
    
 
    
    
}

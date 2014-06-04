# README #

DESCRIPTION:

This application is running on raspberry pi which will get its information from IOIO OTG board
over serial port using pin no 15,16 and GND.

The received data is processed. If internet connectivity is available then received data is time stamped and forwarded to two servlets, one for storage to online database and other to live display page if used.

HARDWARE:
1. Connect two LEDs on pin no. GPIO 18, GPIO 23, GPIO 24 and GPIO 25 via 220E resister. 
2. Connect Switches on pin no. GPIO GPIO 7, GPIO 0, GPIO 2 and GPIO 3
3. Connect serial port on pin no. GPIO 14,GPIO 15 and GND

USAGE:
This application run on raspberry pi using Raspbian Wheezy.
First we have to install JAVA EMBEDDED SUITE 7.0 
Then installed Pi4J library.
After this copy hellostorage.jar file present in the "dist" folder.
This application will need following libraries to run.

   derby.jar:
   
   httpclient-4.3.3.jar

   httpcore-4.3.2.jar

   commons-logging-1.1.3.jar
   
   pi4j-core.jar

   pi4j-device.jar

   pi4j-gpio-extension.jar

   pi4j-service.jar

   json-simple-1.1.1.jar:




We can also put all jars in one folder and execute following command.

COMMAND:
 
java -classpath .:classes:/opt/pi4j/jagdeep/'*':/opt/pi4j/hellostorage.jar         com.oracle.jes.samples.hellostorage.SerialCommn
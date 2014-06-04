/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oracle.jes.samples.hellostorage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author pi
 */
public class CheckInternet {
    
    public static boolean testInet(String site) {
    Socket sock = new Socket();
    InetSocketAddress addr = new InetSocketAddress(site,8080);
    try {
        sock.connect(addr,3000);
        return true;
    } catch (IOException e) {
        return false;
    } finally {
        try {sock.close();}
        catch (IOException e) {}
    }
}
    
}

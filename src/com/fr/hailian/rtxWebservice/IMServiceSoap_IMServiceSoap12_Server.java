
package com.fr.hailian.rtxWebservice;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-08-11T15:09:24.697+08:00
 * Generated source version: 3.1.12
 * 
 */
 
public class IMServiceSoap_IMServiceSoap12_Server{

    protected IMServiceSoap_IMServiceSoap12_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new IMServiceSoap12Impl();
        String address = "http://10.0.6.41:8089/NWHServiceIM.asmx";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new IMServiceSoap_IMServiceSoap12_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}

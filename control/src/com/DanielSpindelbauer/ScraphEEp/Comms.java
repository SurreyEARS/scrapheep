/**
 * Main.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.UUID;


/**
 * @author Daniel Spindelbauer
 *
 */
public class Comms {
  private static class Validator {
    private static final String IPADDRESS_PATTERN =
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
    private static Matcher matcher;
        
    public static boolean IP(final String ip) {
      matcher = pattern.matcher(ip);
      return matcher.matches();
    }
  }
  
  private InetAddress ip;
  private Connection conn;
  private DatagramSocket socket;
  private Thread connectionThread;
  private byte valueToSend = 0;
//  UUID FormatID0 = UUID.fromString("CEAC9A68-E109-47B9-A134-F6B78DF82631"); // ??? 
  
  /**
   * Constructor. Set field values.
   *
   * @param ip
   * @throws IllegalArgumentException
   */
  public Comms(String ip) throws IllegalArgumentException, UnknownHostException, SocketException {
    if (!Validator.IP(ip)) { // validate input
      throw new IllegalArgumentException("IP is invalid");
    }
    try {
      this.ip = InetAddress.getByName(ip);
    } catch (UnknownHostException e1) {
      e1.printStackTrace();
      throw e1;
    }
    try {
      this.socket = new DatagramSocket();
    } catch (SocketException e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public void connect() throws Exception {
    try {
      System.out.println("Connecting...");
      
      conn = new Connection();
      connectionThread = new Thread(conn);
      connectionThread.start();
    } catch (Exception e) {
//      e.printStackTrace();
      System.out.println("Error when connecting to ESP, check code (this is from Comms.connect())");
      throw e;
    }
  }
  
  private class Connection implements Runnable {
    public Connection() {}
    
    public void run() {
      while (true) {
        try {
          Thread.sleep(10);
          byte[] outData = new byte[1024];
          outData = Byte.toString(valueToSend).getBytes();
          DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, ip, 4210);
          socket.send(sendPkt);

          System.out.println("sent " + valueToSend);
        } catch (Exception e) {
          e.printStackTrace();
          break;
        }
      }
      
      disconnect(); // TODO
    }
  } // End class
  
  /**
   * Close connection to socket
   */
  public void disconnect() {
    if (socket.isConnected()) {
      socket.close();
    }
    
    // TODO safely shut down data stream
    connectionThread.interrupt();
  }
  
  public void setValue(int value) {
    this.valueToSend |= value;
  }
  
  public void clearValue(int value) {
    this.valueToSend &= ~value;
  }
  
  public void stopValue() {
    this.valueToSend = 0;
  }
} // End class

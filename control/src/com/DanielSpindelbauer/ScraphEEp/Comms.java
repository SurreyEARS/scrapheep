/**
 * Comms.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Daniel Spindelbauer
 *
 */
public class Comms {
  /**
   * Encapsulating regex matching for IP validation
   */
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
  
  /**
   * Constructor. Set field values.
   *
   * @param ip: IP of ESP
   * @throws IllegalArgumentException: invalid IP
   * @throws UnknownHostException: can be sent by InetAddress for IP
   * @throws SocketException: in case something goes wrong with socket init
   */
  public Comms(String ip) throws IllegalArgumentException, UnknownHostException, SocketException {
    if (!Validator.IP(ip)) { // validate input
      throw new IllegalArgumentException("IP is invalid");
    }
    try {
      this.ip = InetAddress.getByName(ip);
      this.socket = new DatagramSocket();
    } catch (UnknownHostException | SocketException e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  /**
   * Init connection to ESP
   * 
   * @throws Exception: when something goes wrong
   */
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
  
  
  /**
   * Set value to send to ESP
   * 
   * @param value: values to send as byte (each bit representing something, check other side for that)
   * @param forwards: which direction the motors should turn 
   */
  public void setValue(byte value, boolean forwards) {
    if (forwards) {
      this.valueToSend |= value;
    } else {
      this.valueToSend &= ~value;
    }
  }
  
  /**
   * @author Daniel Spindelbauer
   *
   * Separate thread for maintaining data stream connection to server 
   */
  private class Connection implements Runnable {
    public Connection() {}
    
    public void run() {
      while(true) { // loop
        try {
          Thread.sleep(10);
          byte[] outData = new byte[8];
          outData = Byte.toString(valueToSend).getBytes();
          DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, ip, 4210);
          socket.send(sendPkt);
          
          System.out.println("sent: " + valueToSend);
        } catch (Exception e) {
          e.printStackTrace();
          break;
        }
      }
      disconnect();
    }
  } // End class
  
} // End class

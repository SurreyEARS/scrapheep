/**
 * Comms.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Daniel Spindelbauer
 *
 */
public class Comms implements Observer {
  /**
   * Encapsulating pattern matching for IP validation
   */
  private static class Validator {
    private static final String IPADDRESS_PATTERN =
        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private static Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
    private static Matcher matcher = null;
        
    public static boolean IP(final String ip) {
      matcher = pattern.matcher(ip);
      return matcher.matches();
    }
  }
  
  private InetAddress ip;
  private Connection conn;
  private DatagramSocket socket;
  private Thread connectionThread;
  private DataPacket data;
  private boolean shouldSend = false;
  
  /**
   * Constructor. Set field values.
   *
   * @param ip: IP of ESP
   * @throws IllegalArgumentException: invalid IP
   * @throws UnknownHostException: can be sent by InetAddress for IP
   * @throws SocketException: in case something goes wrong with socket init
   */
  public Comms(String ip) throws IllegalArgumentException, UnknownHostException, SocketException {
    super();
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
    this.data = new DataPacket();
    this.data.addObserver(this);
  }
  
  @Override
  public void update(Observable o, Object arg) {
    if (o instanceof DataPacket) {
      this.data = (DataPacket) o;
      System.out.println("Data is now " + this.data.getValueToSend());
      this.shouldSend = true;
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
      
      this.conn = new Connection();
      this.connectionThread = new Thread(conn);
      this.connectionThread.start();
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
    this.data.setValueToSend((byte) 0, true);
    try {
      this.sendData();
    } catch (IOException e) {
      // ¯\_(ツ)_/¯
    }
    
    if (this.socket.isConnected()) {
      this.socket.close();
    }
    
    // TODO safely shut down data stream
    this.connectionThread.interrupt();
    System.out.println("Disconnected");
  }
  
  
  /**
   * Set value to send to ESP
   * 
   * @param value: values to send as byte (each bit representing something, check other side for that)
   * @param forwards: which direction the motors should turn 
   */
  public void setValue(byte value, boolean forwards) {
    this.data.setValueToSend(value, forwards);
  }
  
  private void sendData() throws IOException {
    byte[] outData = new byte[1];
    outData[0] = this.data.getValueToSend();
    DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, this.ip, 4210);
    try {
      this.socket.send(sendPkt);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
    
    System.out.println(String.format("sent: %8s", Integer.toBinaryString(this.data.getValueToSend() & 0xFF)).replace(' ', '0'));
  }
  
  /**
   * Separate thread for maintaining data stream connection to server
   * 
   * @author Daniel Spindelbauer 
   */
  private class Connection implements Runnable {
    public Connection() { super(); }
    
    public void run() {
      while (true) { // loop
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
          break;
        }
        if (shouldSend) { // only send if data changed flag is set
          try {
            sendData();
            shouldSend = !shouldSend; // sent data, change flag
          } catch (IOException e) {
            // ¯\_(ツ)_/¯
          }
        }
      }
      disconnect(); // loop broke, disconnect 
    }
  } // End class
  
} // End class

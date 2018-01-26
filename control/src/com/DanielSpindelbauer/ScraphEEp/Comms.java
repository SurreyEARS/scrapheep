/**
 * Main.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
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
  
  private String ip;
  private static Socket socket;
  private Connection conn;
  private Thread connectionThread;
  private byte valueToSend = 0;
//  UUID FormatID0 = UUID.fromString("CEAC9A68-E109-47B9-A134-F6B78DF82631"); // ??? 
  
  /**
   * Constructor. Set field values.
   *
   * @param ip
   * @throws IllegalArgumentException
   */
  public Comms(String ip) throws IllegalArgumentException {
    if (!Validator.IP(ip)) { // validate input
      throw new IllegalArgumentException("IP is invalid");
    }
    this.ip = ip;
  }
  
  public boolean isConnected() {
    return socket.isConnected();
  }
  
  public void connect() throws Exception {
    try {
      System.out.println("Connecting...");
      socket = new Socket();
      socket.connect(new InetSocketAddress(this.ip, 1647), 400);
      socket.setSoTimeout(400);
      socket.setTcpNoDelay(true);
      
      System.out.println("Connected! :D");
      
      conn = new Connection(socket.getOutputStream()); 
      connectionThread = new Thread(conn);
      connectionThread.start();
    } catch (Exception e) {
//      e.printStackTrace();
      System.out.println("Error when connecting to ESP, check code (this is from Comms.connect())");
      throw e;
    }
  }
  
  private class Connection implements Runnable {
    private DataOutputStream output;
    
    public Connection(OutputStream outputStream) {
      this.output = new DataOutputStream(outputStream);
    }
    
    long time = System.currentTimeMillis();
    
    public void run() {
      while (socket.isConnected()) {
        try {
          Thread.sleep(10);

          output.writeInt(Integer.reverseBytes(0xCEAC9A68));
          output.writeShort(Short.reverseBytes((short)0xE109));
          output.writeShort(Short.reverseBytes((short)0x47B9));
          output.write(new byte[] {(byte)0xA1, (byte)0x34, (byte)0xF6, (byte)0xB7, (byte)0x8D, (byte)0xF8, (byte)0x26, (byte)0x31});

          output.writeByte(1);
          
          if (System.currentTimeMillis() - time > 1000)  {
//            output.writeByte(0B1111);
//            output.writeByte(valueToSend);
//            System.out.println("1");
            time = System.currentTimeMillis();
            if (System.currentTimeMillis() - time > 2000) {
//              System.out.println("2");
              
//              valueToSend = 0;
            }
          } else {
            output.writeByte(valueToSend);
//            System.out.println("3");
          }

          output.write(new byte[1024]);
        } catch (Exception e) {
          e.printStackTrace();
          break;
        }
      }
      
      disconnect(); // TODO
    }
  }
  
  /**
   * Close connection to socket
   */
  public void disconnect() {
    // TODO safely shut down data stream
//    try {
//      connectionThread.join(400);
//    }
//    catch (InterruptedException e1) {
//      // TODO Auto-generated catch block
//      System.out.println("Comms.disconnect()");
//      e1.printStackTrace();
//    }
    
    if (socket.isConnected()) {
      try {
        socket.close();
      } catch (IOException e) { // Can't close socket
        e.printStackTrace();
      }
    }
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

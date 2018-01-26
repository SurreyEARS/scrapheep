/**
 * Main.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.io.DataOutputStream;
import java.io.IOException;
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
  static byte ValueToSend = 0;
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
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());
      // DataInputStream input = new DataInputStream(socket.getInputStream());
      
      sendData(output);
      
      System.out.println("Connected! :D");
    } catch (Exception e) {
//      e.printStackTrace();
      System.out.println("Error when connecting to ESP, check code (this is from Comms.connect())");
      throw e;
    }
  }
  
  private void sendData(DataOutputStream data) {
    while (socket.isConnected()) {
      try {
        Thread.sleep(10);

        data.writeInt(Integer.reverseBytes(0xCEAC9A68));
        data.writeShort(Short.reverseBytes((short)0xE109));
        data.writeShort(Short.reverseBytes((short)0x47B9));
        data.write(new byte[] {(byte)0xA1, (byte)0x34, (byte)0xF6, (byte)0xB7, (byte)0x8D, (byte)0xF8, (byte)0x26, (byte)0x31});

        data.writeByte(1);
        
        // if (System.currentTimeMillis() - time > 1000)  {
        //  output.writeByte(0B1111);
        //  if (System.currentTimeMillis() - time > 2000)
        //  time = System.currentTimeMillis();
        // } else {
        //  output.writeByte(ValueToSend);
        // }

        data.write(new byte[1024]);
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }
    
    disconnect();
  }
  
  /**
   * Close connection to socket
   */
  public void disconnect() {
    if (socket.isConnected()) {
      try {
        socket.close();
      } catch (IOException e) { // Can't close socket
        e.printStackTrace();
      }
    }
  }
} // End class

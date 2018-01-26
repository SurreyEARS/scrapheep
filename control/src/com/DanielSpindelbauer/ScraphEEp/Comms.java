/**
 * Main.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;
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
        
    public static boolean validate(final String ip) {
      matcher = pattern.matcher(ip);
      return matcher.matches();
    }
  }
  
  private String ip;
  private static Socket socket;
  static byte ValueToSend = 0;
 
  /**
   * Constructor. Set field values.
   *
   * @param ip
   * @throws IllegalArgumentException
   */
  public Comms(String ip) throws IllegalArgumentException {
    if (!Validator.validate(ip)) {
      throw new IllegalArgumentException("IP is invalid");
    }
    this.ip = ip;
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("IP of ESP: ");
    String ip = scanner.nextLine(); // Get IP from user
    
    while(!validate(ip)) {
      System.out.print("Wrong IP, try again, IP of ESP: ");
      ip = scanner.nextLine(); // Get IP from user
    }
    scanner.close();
//    UUID FormatID0 = UUID.fromString("CEAC9A68-E109-47B9-A134-F6B78DF82631"); // ???
    
    JFrame window = new JFrame("Remote control"); // init window for control
    window.setPreferredSize(new Dimension(640, 480)); // set size
    window.pack(); // set size
    window.setLocationRelativeTo(null);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
    
    // listen for key inputs
    window.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}
      
      @Override
      public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_Q:
            ValueToSend &= ~0B0110;
            break;
          case KeyEvent.VK_E:
            ValueToSend &= ~0B1000;
            break;
          case KeyEvent.VK_A:
            ValueToSend &= ~0B0100;
            break;
          case KeyEvent.VK_D:
            ValueToSend &= ~0B1001;
            break;
          default:
        }
      }
      
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_Q:
            ValueToSend |= 0B0110;
            break;
          case KeyEvent.VK_E:
            ValueToSend |= 0B1000;
            break;
          case KeyEvent.VK_A:
            ValueToSend |= 0B0100;
            break;
          case KeyEvent.VK_D:
            ValueToSend |= 0B1001;
            break;
          default:
        }
      }
    });
  
//    long time = System.currentTimeMillis();
    while(true) {
      
    } // End while

  } // End main
  
  
  public boolean connect() {
    try {
      System.out.println("Connecting...");
      socket = new Socket(this.ip, 1647);
      socket.setTcpNoDelay(true);
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());
      // DataInputStream input = new DataInputStream(socket.getInputStream());
      
      System.out.println("Connected! :D");
      return true;
          
      while (socket.isConnected()) {
        try {
          Thread.sleep(10);
  
          output.writeInt(Integer.reverseBytes(0xCEAC9A68));
          output.writeShort(Short.reverseBytes((short)0xE109));
          output.writeShort(Short.reverseBytes((short)0x47B9));
          output.write(new byte[] {(byte)0xA1, (byte)0x34, (byte)0xF6, (byte)0xB7, (byte)0x8D, (byte)0xF8, (byte)0x26, (byte)0x31});
  
          output.writeByte(1);
          
          // if (System.currentTimeMillis() - time > 1000)  {
          //  output.writeByte(0B1111);
          //  if (System.currentTimeMillis() - time > 2000)
          //  time = System.currentTimeMillis();
          // } else {
          //  output.writeByte(ValueToSend);
          // }

          output.write(new byte[1024]);
        } catch (Exception e) {
          e.printStackTrace();
          break;
        }
      }
      
      if (socket.isConnected()) {
        socket.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error when connecting to ESP, check stacktrace");
      return false;
    }
  }
} // End class

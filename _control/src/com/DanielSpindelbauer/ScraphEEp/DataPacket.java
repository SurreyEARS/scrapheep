/**
 * DataPacket.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.util.Arrays;
import java.util.Observable;

/**
 * @author Daniel Spindelbauer
 *
 */
public class DataPacket extends Observable {
  
  private byte leftMotor;
  private byte rightMotor;

  private byte control1;
  private byte control2;
  private byte control3;
  private byte control4;
  
  /**
   * 0 leftMotor
   * 1 rightMotor
   * 2 control1
   * 3 control2
   * 4 control3
   * 5 control4
   */
  private byte[] valuesToSend;
  
  private boolean controlIsCoord = false;
  
  /**
   * Constructor. Set field values.
   *
   */
  public DataPacket() {
    super();
    this.leftMotor = 0;
    this.rightMotor = 0;
    
    this.control1 = 0;
    this.control2 = 0;
    this.control3 = 0;
    this.control4 = 0;

    this.valuesToSend = new byte[6];
  }

  public synchronized byte[] getValuesToSend() {
    this.valuesToSend[0] = this.leftMotor;
    this.valuesToSend[1] = this.rightMotor;
    this.valuesToSend[2] = this.control1;
    this.valuesToSend[3] = this.control2;
    this.valuesToSend[4] = this.control3; 
    this.valuesToSend[5] = this.control4;
    return this.valuesToSend;
  }
  
  public void clearControls() {
    synchronized (this) {
      this.leftMotor = 0;
      this.rightMotor = 0;
      this.control1 = 0;
      this.control2 = 0;
      this.control3 = 0; 
      this.control4 = 0; 
    }
    setChanged();
    notifyObservers();
  }
  
  /**
   * @param isCoord: left and right motor values are coordinates
   */
  public void setControlType(boolean isCoord) {
    this.controlIsCoord = isCoord; 
  }
  
  /**
   * @param x: x position of joystick
   * @param y: y position of joystick
   */
  public void setMotors(int x, int y) {
//    System.out.println("x: " + x + ", y: "+ y);
    synchronized (this) {
      if (controlIsCoord) {
        this.leftMotor = (byte) x;
        this.rightMotor = (byte) y;
      } else {
        int left = y+x;
        int right = y-x;
        
        if (left == 256) {
          left = 255;
        }
        if (right == 256) {
          right = 255;
        }
        if (left == -256) {
          left = -255;
        }
        if (right == -256) {
          right = -255;
        }
//        byte l = (byte) (0xff&left);
//        byte r = (byte) (0xff&right);
        System.out.println(left + ", " + String.format("%8s", Integer.toBinaryString((byte) left & 0xFF)).replace(' ', '0') );
        System.out.print("left: " + left + ", "  + " -- "); 
        System.out.println("right: "+ right + ", ");
        this.leftMotor = (byte) left;
        this.rightMotor = (byte) right;
      } 
    }
    setChanged();
    notifyObservers();
  }
  
  /**
   * @param control1 the control1 to set
   */
  public void setControl1(byte control1) {
    synchronized (this) {
      this.control1 = control1;
    }
    setChanged();
    notifyObservers();
  }
  
  /**
   * @param control2 the control2 to set
   */
  public void setControl2(byte control2) {
    synchronized (this) {
      this.control2 = control2;
    }
    setChanged();
    notifyObservers();
  }

  /**
   * @param control3 the control3 to set
   */
  public void setControl3(byte control3) {
    synchronized (this) {
      this.control3 = control3;
    }
    setChanged();
    notifyObservers();
  }
  
  /**
   * @param control4 the control4 to set
   */
  public void setControl4(byte control4) {
    synchronized (this) {
      this.control4 = control4;
    }
    setChanged();
    notifyObservers();
  }

  /**
   * Puts together the answer in a specified format then prints & returns it
   *
   * @return
   */
  @Override
  public String toString() {
    return "DataPacket [leftMotor=" + leftMotor + ", rightMotor=" + rightMotor + ", control1=" + control1 + ", control2=" + control2
        + ", control3=" + control3 + ", control4=" + control4 + ", valuesToSend=" + Arrays.toString(valuesToSend)
        + ", controlIsCoord=" + controlIsCoord + "]";
  }

} // End class

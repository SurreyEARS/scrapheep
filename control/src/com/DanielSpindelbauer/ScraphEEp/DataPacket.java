/**
 * DataPacket.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.util.Observable;

/**
 * @author Daniel Spindelbauer
 *
 */
public class DataPacket extends Observable {
  
  private byte valueToSend;

  /**
   * Constructor. Set field values.
   *
   */
  public DataPacket() {
    super();
    this.valueToSend = 0;
  }
  
  public void setValueToSend(byte value, boolean forwards) {
    synchronized (this) {
      if (forwards) {
        this.valueToSend |= value;
      } else {
        this.valueToSend &= ~value;
      }
    }
    setChanged();
    notifyObservers(); //(this.valueToSend)
  }

  public synchronized byte getValueToSend() {
    return this.valueToSend;
  }

} // End class

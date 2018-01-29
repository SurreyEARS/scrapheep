/**
 * PointChangeEvent.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.awt.Point;
import javax.swing.event.ChangeEvent;

/**
 * @author
 *
 */
public class PointChangeEvent extends ChangeEvent {
  private static final long serialVersionUID = 1L;
  public Point p;
  
  /**
   * Constructor. Set field values.
   *
   * @param source
   * @param p
   */
  public PointChangeEvent(Object source, Point p) {
    super(source);
    this.p = p;
  }
} // End class

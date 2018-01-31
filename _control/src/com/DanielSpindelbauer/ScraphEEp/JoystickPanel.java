/**
 * JoystickPanel.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * @author 
 *
 */
public class JoystickPanel extends JPanel implements ChangeListener {

  private JLabel lblPosition;
  private static final long serialVersionUID = 1L;
  private Comms comms = null;
  
  public JoystickPanel() {
    setLayout(new BorderLayout(0, 0));
    SimpleJoystick myJoystick = new SimpleJoystick(64);
    myJoystick.setPreferredSize(new Dimension(300, 300));
    myJoystick.addChangeListener(this);
    add(myJoystick, BorderLayout.CENTER);

    lblPosition = new JLabel("position");
    add(lblPosition, BorderLayout.SOUTH);
  }

  public void setComms(Comms c) {
    this.comms = c;
  }
  
  @Override
  public void stateChanged(ChangeEvent ev) {
    Point p = null;
    
    try {
      p = ((PointChangeEvent) ev).p;
    } catch (Exception e) {
      return;
    }
    
    lblPosition.setText("x=" + p.x + " y=" + p.y);
    comms.data.setMotors(p.x, p.y);
  }

} // End class

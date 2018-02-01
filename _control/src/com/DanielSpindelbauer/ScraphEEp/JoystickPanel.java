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

public class JoystickPanel extends JPanel implements ChangeListener
{
	private JLabel lblPosition;
	private static final long serialVersionUID = 1L;
	private Comms comms = null;
	SimpleJoystick joystick;

	public JoystickPanel()
	{
		setLayout(new BorderLayout(0, 0));
		joystick = new SimpleJoystick(64);
		setEnabled(false);
		joystick.setPreferredSize(new Dimension(300, 300));
		joystick.addChangeListener(this);
		add(joystick, BorderLayout.CENTER);

		lblPosition = new JLabel("position");
		add(lblPosition, BorderLayout.SOUTH);
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		joystick.setEnabled(enabled);
	}

	public void setComms(Comms c)
	{
		this.comms = c;
	}

	@Override
	public void stateChanged(ChangeEvent ev)
	{
		Point p = null;

		try
		{
			p = ((PointChangeEvent) ev).p;
		}
		catch (Exception e)
		{
			return;
		}

		lblPosition.setText("x=" + p.x + " y=" + p.y);
		comms.data.setMotors(p.x, p.y);
	}

} // End class

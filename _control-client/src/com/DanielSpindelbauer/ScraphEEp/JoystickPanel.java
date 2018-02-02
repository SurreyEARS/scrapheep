package com.DanielSpindelbauer.ScraphEEp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JoystickPanel extends JPanel implements ChangeListener
{
	private JLabel lblPosition;
	private static final long serialVersionUID = 1L;
	private Connection comms;
	public SimpleJoystick joystick;

	public JoystickPanel(Connection connection)
	{
		this.comms = connection;
		setLayout(new BorderLayout(0, 0));
		joystick = new SimpleJoystick(128);
		setEnabled(false);
		joystick.setPreferredSize(new Dimension(300, 300));
		joystick.addChangeListener(this);
		add(joystick, BorderLayout.CENTER);

		lblPosition = new JLabel("position");
		add(lblPosition, BorderLayout.SOUTH);

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyDispatcher());
	}

	class KeyDispatcher implements KeyEventDispatcher
	{
		boolean keys[] = new boolean[65535];

		public boolean dispatchKeyEvent(KeyEvent e)
		{
			if (e.getID() == KeyEvent.KEY_PRESSED)
				keys[e.getKeyCode()] = true;
			else if (e.getID() == KeyEvent.KEY_RELEASED)
				keys[e.getKeyCode()] = false;
			keyEvent();

			return false;
		}

		private void keyEvent()
		{
			if (comms.isConnected())
			{
				if (keys[KeyEvent.VK_NUMPAD1])
					joystick.position.setLocation(-127, -127);
				else if (keys[KeyEvent.VK_NUMPAD2])
					joystick.position.setLocation(0, -127);
				else if (keys[KeyEvent.VK_NUMPAD3])
					joystick.position.setLocation(127, -127);
				else if (keys[KeyEvent.VK_NUMPAD4])
					joystick.position.setLocation(-127, 0);
				else if (keys[KeyEvent.VK_NUMPAD5])
					joystick.position.setLocation(0, 0);
				else if (keys[KeyEvent.VK_NUMPAD6])
					joystick.position.setLocation(127, 0);
				else if (keys[KeyEvent.VK_NUMPAD7])
					joystick.position.setLocation(-127, 127);
				else if (keys[KeyEvent.VK_NUMPAD8])
					joystick.position.setLocation(0, 127);
				else if (keys[KeyEvent.VK_NUMPAD9])
					joystick.position.setLocation(127, 127);
				else
				{
						joystick.position.setLocation(0, 0);
						joystick.fireStateChanged();
						joystick.repaint();
				}
				Point position = joystick.position;
				double dist = Math.sqrt(position.x * position.x + position.y * position.y);
				if (dist > 128)
				{
					position.x *= 128.0 / dist;
					position.y *= 128.0 / dist;
				}
				joystick.position = position;
				joystick.repaint();
				joystick.fireStateChanged();
			}
	}

	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		joystick.setEnabled(enabled);
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
		
		comms.data.setMotors(p.x, p.y);

		if (comms.data.controlIsCoord)
			lblPosition.setText("x=" + p.x + " y=" + p.y);
		else
			lblPosition.setText("MotorA=" + comms.data.leftMotor + " MotorB=" + comms.data.rightMotor);
	}

}

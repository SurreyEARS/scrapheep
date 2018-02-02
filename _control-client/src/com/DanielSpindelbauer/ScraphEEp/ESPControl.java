package com.DanielSpindelbauer.ScraphEEp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ESPControl
{
	private ESPWindow window;
	private Connection connection;

	public static void main(String[] args)
	{
		new ESPControl();
	}

	/**
	 * Create the application.
	 */
	public ESPControl()
	{		
		connection = new Connection();
		window = new ESPWindow(this);
		connection.addStatusListener(window);
		addListeners();
		window.setVisible(true);
	}

	private void addListeners()
	{
		window.btnConnect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (connection.isConnected())
					connection.disconnect();
				else
					actionConnect();
			}
		});

		window.frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				connection.disconnect();
				window.close();
			}
		});
		window.btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				connection.disconnect();
				window.close();
			}
		});

		window.txtIp.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					actionConnect();
			}
		});

		window.rdbtnMotor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				connection.data.setControlType(false);
				window.joystick.joystick.fireStateChanged();
			}
		});

		window.rdbtnCoord.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				connection.data.setControlType(true);
				window.joystick.joystick.fireStateChanged();
			}
		});

		window.sliderC1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC1.setText(String.valueOf(window.sliderC1.getValue()));
				connection.data.setControl1((byte) window.sliderC1.getValue());
			}
		});
		window.sliderC2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC2.setText(String.valueOf(window.sliderC2.getValue()));
				connection.data.setControl2((byte) window.sliderC2.getValue());
			}
		});
		window.sliderC3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC3.setText(String.valueOf(window.sliderC3.getValue()));
				connection.data.setControl3((byte) window.sliderC3.getValue());
			}
		});
		window.sliderC4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC4.setText(String.valueOf(window.sliderC4.getValue()));
				connection.data.setControl4((byte) window.sliderC4.getValue());
			}
		});
	}

	// MARK: - Connection actions
	/**
	 * Start connection and set up elements
	 */
	private void actionConnect()
	{
		String address = window.txtIp.getText();
		if (!address.isEmpty())
		{
			try
			{
				connection.connect(address);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Error when connecting", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Enter IP address of ESP", "No IP address", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Connection getConnection()
	{
		return connection;
	}
}

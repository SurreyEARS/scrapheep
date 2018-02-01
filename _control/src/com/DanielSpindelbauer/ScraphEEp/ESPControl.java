package com.DanielSpindelbauer.ScraphEEp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.DanielSpindelbauer.ScraphEEp.ESPWindow.WindowStatus;

public class ESPControl
{
	private ESPWindow window;
	
	private static Comms comms = null;
	private boolean isConnected = false;


	public static void main(String[] args)
	{
		new ESPControl();
	}

	/**
	 * Create the application.
	 */
	public ESPControl()
	{
		window = new ESPWindow();
		addListeners();
		window.setVisible(true);
	}
	
	private void addListeners()
	{
		window.btnConnect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (isConnected)
					actionDisconnect();
				else
					actionConnect();
				isConnected = !isConnected;
			}
		});

		window.btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (comms != null)
					comms.disconnect();
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

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					actionConnect();
			}
		});

		window.rdbtnMotor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				comms.data.setControlType(false);
			}
		});

		window.rdbtnCoord.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				comms.data.setControlType(true);
			}
		});

		window.sliderC1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC1.setText(String.valueOf(window.sliderC1.getValue()));
				comms.data.setControl1((byte) window.sliderC1.getValue());
			}
		});
		window.sliderC2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC2.setText(String.valueOf(window.sliderC2.getValue()));
				comms.data.setControl2((byte) window.sliderC2.getValue());
			}
		});
		window.sliderC3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC3.setText(String.valueOf(window.sliderC3.getValue()));
				comms.data.setControl3((byte) window.sliderC3.getValue());
			}
		});
		window.sliderC4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				window.labelC4.setText(String.valueOf(window.sliderC4.getValue()));
				comms.data.setControl4((byte) window.sliderC4.getValue());
			}
		});
	}

	// MARK: - Connection actions
	/**
	 * Start connection and set up elements
	 */
	private void actionConnect()
	{
		if (!window.txtIp.getText().isEmpty())
		{ // check if ip is entered
			try
			{
				ESPControl.comms = new Comms(this, window.txtIp.getText());
				comms.connect();
				window.txtConnectedTo.setText("Connected to " + window.txtIp.getText());
				window.joystick.setComms(ESPControl.comms);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Error when connecting", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Close connection and set up elements
	 */
	private void actionDisconnect()
	{
		comms.disconnect();
	}
	
	public ESPWindow getWindow()
	{
		return window;
	}
}

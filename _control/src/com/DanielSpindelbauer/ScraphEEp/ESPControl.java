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
<<<<<<< HEAD
	
=======

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("ESP Control");
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// MARK: - MenuBar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		btnExit = new JButton("Exit");
		menuBar.add(btnExit);

		// MARK: - CONTENT
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]
		{
				0, 102, 0, 0, 50
		};
		gridBagLayout.rowHeights = new int[]
		{
				0, 0, 0, 0, 0, 0, 0, 0
		};
		gridBagLayout.columnWeights = new double[]
		{
				0.0, 1.0, 0.0, 1.0, 0.0
		};
		gridBagLayout.rowWeights = new double[]
		{
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0
		};
		frame.getContentPane().setLayout(gridBagLayout);

		txtConnectedTo = new JTextField();
		txtConnectedTo.setEditable(false);
		txtConnectedTo.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		txtConnectedTo.setText("Waiting...");
		GridBagConstraints gbc_txtConnectedTo = new GridBagConstraints();
		gbc_txtConnectedTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConnectedTo.gridwidth = 5;
		gbc_txtConnectedTo.insets = new Insets(0, 0, 5, 0);
		gbc_txtConnectedTo.gridx = 0;
		gbc_txtConnectedTo.gridy = 0;
		frame.getContentPane().add(txtConnectedTo, gbc_txtConnectedTo);
		txtConnectedTo.setColumns(10);

		// MARK: - IP input field
		txtIp = new JTextField();
		txtIp.setText("");
		txtIp.setToolTipText("0.0.0.0");
		GridBagConstraints gbc_ip = new GridBagConstraints();
		gbc_ip.gridwidth = 3;
		gbc_ip.insets = new Insets(0, 0, 5, 5);
		gbc_ip.fill = GridBagConstraints.HORIZONTAL;
		gbc_ip.gridx = 0;
		gbc_ip.gridy = 1;
		frame.getContentPane().add(txtIp, gbc_ip);
		txtIp.setColumns(10);

		btnConnect = new JButton("Connect");
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 3;
		gbc_btnConnect.gridy = 1;
		frame.getContentPane().add(btnConnect, gbc_btnConnect);

		lblValuesToSend = new JLabel("Values to send");
		GridBagConstraints gbc_lblValuesToSend = new GridBagConstraints();
		gbc_lblValuesToSend.anchor = GridBagConstraints.EAST;
		gbc_lblValuesToSend.gridwidth = 2;
		gbc_lblValuesToSend.insets = new Insets(0, 0, 5, 5);
		gbc_lblValuesToSend.gridx = 0;
		gbc_lblValuesToSend.gridy = 2;
		frame.getContentPane().add(lblValuesToSend, gbc_lblValuesToSend);

		rdbtnMotor = new JRadioButton("Motor");
		rdbtnMotor.setEnabled(false);
		rdbtnMotor.setSelected(true);
		GridBagConstraints gbc_rdbtnMotor = new GridBagConstraints();
		gbc_rdbtnMotor.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMotor.gridx = 2;
		gbc_rdbtnMotor.gridy = 2;
		frame.getContentPane().add(rdbtnMotor, gbc_rdbtnMotor);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnMotor);

		rdbtnCoord = new JRadioButton("Coord");
		rdbtnCoord.setEnabled(false);
		GridBagConstraints gbc_rdbtnCoord = new GridBagConstraints();
		gbc_rdbtnCoord.anchor = GridBagConstraints.WEST;
		gbc_rdbtnCoord.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnCoord.gridx = 3;
		gbc_rdbtnCoord.gridy = 2;
		frame.getContentPane().add(rdbtnCoord, gbc_rdbtnCoord);
		group.add(rdbtnCoord);

		button0C1 = new JButton("0");
		button0C1.setEnabled(false);
		GridBagConstraints gbc_button0C1 = new GridBagConstraints();
		gbc_button0C1.insets = new Insets(0, 0, 5, 5);
		gbc_button0C1.gridx = 0;
		gbc_button0C1.gridy = 3;
		frame.getContentPane().add(button0C1, gbc_button0C1);

		sliderC1 = new JSlider();
		sliderC1.setMaximum(255);
		sliderC1.setEnabled(false);
		sliderC1.setValue(0);
		GridBagConstraints gbc_sliderC1 = new GridBagConstraints();
		gbc_sliderC1.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderC1.insets = new Insets(0, 0, 5, 5);
		gbc_sliderC1.gridx = 1;
		gbc_sliderC1.gridy = 3;
		frame.getContentPane().add(sliderC1, gbc_sliderC1);

		button1C1 = new JButton("1");
		button1C1.setEnabled(false);
		GridBagConstraints gbc_button1C1 = new GridBagConstraints();
		gbc_button1C1.insets = new Insets(0, 0, 5, 5);
		gbc_button1C1.gridx = 2;
		gbc_button1C1.gridy = 3;
		frame.getContentPane().add(button1C1, gbc_button1C1);

		labelC1 = new JLabel("0");
		GridBagConstraints gbc_labelC1 = new GridBagConstraints();
		gbc_labelC1.insets = new Insets(0, 0, 5, 5);
		gbc_labelC1.gridx = 3;
		gbc_labelC1.gridy = 3;
		frame.getContentPane().add(labelC1, gbc_labelC1);

		lblControl = new JLabel("C1");
		GridBagConstraints gbc_lblControl = new GridBagConstraints();
		gbc_lblControl.insets = new Insets(0, 0, 5, 0);
		gbc_lblControl.gridx = 4;
		gbc_lblControl.gridy = 3;
		frame.getContentPane().add(lblControl, gbc_lblControl);

		button0C2 = new JButton("0");
		button0C2.setEnabled(false);
		GridBagConstraints gbc_button0C2 = new GridBagConstraints();
		gbc_button0C2.insets = new Insets(0, 0, 5, 5);
		gbc_button0C2.gridx = 0;
		gbc_button0C2.gridy = 4;
		frame.getContentPane().add(button0C2, gbc_button0C2);

		sliderC2 = new JSlider();
		sliderC2.setMaximum(255);
		sliderC2.setEnabled(false);
		sliderC2.setValue(0);
		GridBagConstraints gbc_sliderC2 = new GridBagConstraints();
		gbc_sliderC2.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderC2.insets = new Insets(0, 0, 5, 5);
		gbc_sliderC2.gridx = 1;
		gbc_sliderC2.gridy = 4;
		frame.getContentPane().add(sliderC2, gbc_sliderC2);

		button1C2 = new JButton("1");
		button1C2.setEnabled(false);
		GridBagConstraints gbc_button1C2 = new GridBagConstraints();
		gbc_button1C2.insets = new Insets(0, 0, 5, 5);
		gbc_button1C2.gridx = 2;
		gbc_button1C2.gridy = 4;
		frame.getContentPane().add(button1C2, gbc_button1C2);

		labelC2 = new JLabel("0");
		GridBagConstraints gbc_labelC2 = new GridBagConstraints();
		gbc_labelC2.insets = new Insets(0, 0, 5, 5);
		gbc_labelC2.gridx = 3;
		gbc_labelC2.gridy = 4;
		frame.getContentPane().add(labelC2, gbc_labelC2);

		lblControl_1 = new JLabel("C2");
		GridBagConstraints gbc_lblControl_1 = new GridBagConstraints();
		gbc_lblControl_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblControl_1.gridx = 4;
		gbc_lblControl_1.gridy = 4;
		frame.getContentPane().add(lblControl_1, gbc_lblControl_1);

		button0C3 = new JButton("0");
		button0C3.setEnabled(false);
		GridBagConstraints gbc_button0C3 = new GridBagConstraints();
		gbc_button0C3.insets = new Insets(0, 0, 5, 5);
		gbc_button0C3.gridx = 0;
		gbc_button0C3.gridy = 5;
		frame.getContentPane().add(button0C3, gbc_button0C3);

		sliderC3 = new JSlider();
		sliderC3.setMaximum(255);
		sliderC3.setEnabled(false);
		sliderC3.setValue(0);
		GridBagConstraints gbc_sliderC3 = new GridBagConstraints();
		gbc_sliderC3.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderC3.insets = new Insets(0, 0, 5, 5);
		gbc_sliderC3.gridx = 1;
		gbc_sliderC3.gridy = 5;
		frame.getContentPane().add(sliderC3, gbc_sliderC3);

		button1C3 = new JButton("1");
		button1C3.setEnabled(false);
		GridBagConstraints gbc_button1C3 = new GridBagConstraints();
		gbc_button1C3.insets = new Insets(0, 0, 5, 5);
		gbc_button1C3.gridx = 2;
		gbc_button1C3.gridy = 5;
		frame.getContentPane().add(button1C3, gbc_button1C3);

		labelC3 = new JLabel("0");
		GridBagConstraints gbc_labelC3 = new GridBagConstraints();
		gbc_labelC3.insets = new Insets(0, 0, 5, 5);
		gbc_labelC3.gridx = 3;
		gbc_labelC3.gridy = 5;
		frame.getContentPane().add(labelC3, gbc_labelC3);

		lblControl_2 = new JLabel("C3");
		GridBagConstraints gbc_lblControl_2 = new GridBagConstraints();
		gbc_lblControl_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblControl_2.gridx = 4;
		gbc_lblControl_2.gridy = 5;
		frame.getContentPane().add(lblControl_2, gbc_lblControl_2);

		button0C4 = new JButton("0");
		button0C4.setEnabled(false);
		GridBagConstraints gbc_button0C4 = new GridBagConstraints();
		gbc_button0C4.insets = new Insets(0, 0, 5, 5);
		gbc_button0C4.gridx = 0;
		gbc_button0C4.gridy = 6;
		frame.getContentPane().add(button0C4, gbc_button0C4);

		sliderC4 = new JSlider();
		sliderC4.setMaximum(255);
		sliderC4.setEnabled(false);
		sliderC4.setValue(0);
		GridBagConstraints gbc_sliderC4 = new GridBagConstraints();
		gbc_sliderC4.fill = GridBagConstraints.HORIZONTAL;
		gbc_sliderC4.insets = new Insets(0, 0, 5, 5);
		gbc_sliderC4.gridx = 1;
		gbc_sliderC4.gridy = 6;
		frame.getContentPane().add(sliderC4, gbc_sliderC4);

		button1C4 = new JButton("1");
		button1C4.setEnabled(false);
		GridBagConstraints gbc_button1C4 = new GridBagConstraints();
		gbc_button1C4.insets = new Insets(0, 0, 5, 5);
		gbc_button1C4.gridx = 2;
		gbc_button1C4.gridy = 6;
		frame.getContentPane().add(button1C4, gbc_button1C4);

		labelC4 = new JLabel("0");
		GridBagConstraints gbc_labelC4 = new GridBagConstraints();
		gbc_labelC4.insets = new Insets(0, 0, 5, 5);
		gbc_labelC4.gridx = 3;
		gbc_labelC4.gridy = 6;
		frame.getContentPane().add(labelC4, gbc_labelC4);

		lblControl_3 = new JLabel("C4");
		GridBagConstraints gbc_lblControl_3 = new GridBagConstraints();
		gbc_lblControl_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblControl_3.gridx = 4;
		gbc_lblControl_3.gridy = 6;
		frame.getContentPane().add(lblControl_3, gbc_lblControl_3);

		// MARK: Control buttons
		joystick = new JoystickPanel();
		GridBagConstraints gbc_joystick = new GridBagConstraints();
		gbc_joystick.gridwidth = 5;
		gbc_joystick.fill = GridBagConstraints.BOTH;
		gbc_joystick.gridx = 0;
		gbc_joystick.gridy = 7;
		frame.getContentPane().add(joystick, gbc_joystick);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Listener events for view components
	 */
>>>>>>> branch 'master' of https://github.com/sdaniel55/scrapheep
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
<<<<<<< HEAD
	private void actionConnect()
	{
		if (!window.txtIp.getText().isEmpty())
		{ // check if ip is entered
			try
			{
				ESPControl.comms = new Comms(this, window.txtIp.getText());
=======
	private void actionConnect() {
		if (!txtIp.getText().isEmpty()) { // check if ip is entered
			try {
				ESPControl.comms = new Comms(txtIp.getText()); // init comms
>>>>>>> branch 'master' of https://github.com/sdaniel55/scrapheep
				comms.connect();
<<<<<<< HEAD
				window.txtConnectedTo.setText("Connected to " + window.txtIp.getText());
				window.joystick.setComms(ESPControl.comms);
			}
			catch (Exception e)
			{
=======
				txtIp.setEnabled(false);
				txtConnectedTo.setText("Connected to " + txtIp.getText());
				btnConnect.setText("Disconnect");
				joystick.setComms(ESPControl.comms);
				joystick.setEnabled(true);
				sliderC1.setEnabled(true);
				sliderC2.setEnabled(true);
				sliderC3.setEnabled(true);
				sliderC4.setEnabled(true);
				rdbtnMotor.setEnabled(true);
				rdbtnCoord.setEnabled(true);
				button0C1.setEnabled(true);
				button0C2.setEnabled(true);
				button0C3.setEnabled(true);
				button0C4.setEnabled(true);
				button1C1.setEnabled(true);
				button1C2.setEnabled(true);
				button1C3.setEnabled(true);
				button1C4.setEnabled(true);
			} catch (Exception e) {
>>>>>>> branch 'master' of https://github.com/sdaniel55/scrapheep
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "Error when connecting", JOptionPane.ERROR_MESSAGE);
			}
		} else {
		  JOptionPane.showMessageDialog(null, "Enter IP address of ESP", "No IP address", JOptionPane.ERROR_MESSAGE);
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

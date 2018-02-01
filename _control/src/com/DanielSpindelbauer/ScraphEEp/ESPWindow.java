package com.DanielSpindelbauer.ScraphEEp;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.DanielSpindelbauer.ScraphEEp.ConnectionStatus.ConnectionState;
import com.DanielSpindelbauer.ScraphEEp.ConnectionStatus.StateChangeListener;

public class ESPWindow implements StateChangeListener
{
	private transient ESPControl control;
	
	public JFrame frame;

	public JoystickPanel joystick;

	public JSlider sliderC1;
	public JSlider sliderC2;
	public JSlider sliderC3;
	public JSlider sliderC4;
	public JRadioButton rdbtnMotor;
	public JRadioButton rdbtnCoord;
	private JLabel lblValuesToSend;
	private JLabel lblControl;
	private JLabel lblControl_1;
	private JLabel lblControl_2;
	private JLabel lblControl_3;
	public JLabel labelC1;
	public JLabel labelC2;
	public JLabel labelC3;
	public JLabel labelC4;
	private JButton button0C1;
	private JButton button0C2;
	private JButton button0C4;
	private JButton button0C3;
	private JButton button1C1;
	private JButton button1C2;
	private JButton button1C3;
	private JButton button1C4;
	public JButton btnExit;
	public JTextField txtConnectedTo;
	public JTextField txtIp;
	public JButton btnConnect;

	public ESPWindow(ESPControl control)
	{
		this.control = control;
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("ESP Control");
		frame.setBounds(100, 100, 450, 600);

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
		txtConnectedTo.setText("Idle");
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

		setDefaultListeners();
	}

	private void setDefaultListeners()
	{
		button0C1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC1.setValue(sliderC1.getMinimum());
			}
		});
		button0C2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC2.setValue(sliderC2.getMinimum());
			}
		});
		button0C3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC3.setValue(sliderC3.getMinimum());
			}
		});
		button0C4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC4.setValue(sliderC4.getMinimum());
			}
		});

		button1C1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC1.setValue(sliderC1.getMaximum());
			}
		});
		button1C2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC2.setValue(sliderC2.getMaximum());
			}
		});
		button1C3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC3.setValue(sliderC3.getMaximum());
			}
		});
		button1C4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				sliderC4.setValue(sliderC4.getMaximum());
			}
		});
	}

	public void setVisible(boolean visible)
	{
		frame.setVisible(true);
	}
	
	public void close()
	{
		frame.dispose();
	}

	public void setEnabled(boolean enabled)
	{
		txtIp.setEnabled(!enabled);
		joystick.setEnabled(enabled);
		sliderC1.setEnabled(enabled);
		sliderC2.setEnabled(enabled);
		sliderC3.setEnabled(enabled);
		sliderC4.setEnabled(enabled);
		rdbtnMotor.setEnabled(enabled);
		rdbtnCoord.setEnabled(enabled);
		button0C1.setEnabled(enabled);
		button0C2.setEnabled(enabled);
		button0C3.setEnabled(enabled);
		button0C4.setEnabled(enabled);
		button1C1.setEnabled(enabled);
		button1C2.setEnabled(enabled);
		button1C3.setEnabled(enabled);
		button1C4.setEnabled(enabled);
	}
	
	public synchronized void setStatus(ConnectionState status)
	{
		switch (status)
		{
			case DISCONNECTED:
				btnConnect.setText("Connect");
				btnConnect.setEnabled(true);
				txtConnectedTo.setText("Idle");
				setEnabled(false);
				break;
			case CONNECTING:
				btnConnect.setText("Connect");
				btnConnect.setEnabled(false);
				txtConnectedTo.setText("Connecting to " + control.getConnection().getIP() + "...");
				setEnabled(false);
				break;
			case CONNECTED:
				btnConnect.setText("Disconnect");
				btnConnect.setEnabled(true);
				txtConnectedTo.setText("Connected to " + control.getConnection().getIP());
				setEnabled(true);
		}
	}

	@Override
	public void changed(ConnectionState status)
	{
		setStatus(status);
	}
}

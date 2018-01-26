package com.DanielSpindelbauer.ScraphEEp;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;

import com.DanielSpindelbauer.ScraphEEp.Comms;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ESPControl {

  private JFrame frmEspControl;
  private JTextField txtConnectedTo;
  private JTextField ip;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {

      public void run() {
        try {
          ESPControl window = new ESPControl();
          window.frmEspControl.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public ESPControl() {
    initialize();
  }

  /**
   * Initialise the contents of the frame.
   */
  private void initialize() {
    frmEspControl = new JFrame();
    frmEspControl.setResizable(false);
    frmEspControl.setTitle("ESP Control");
    frmEspControl.setBounds(100, 100, 450, 300);
    frmEspControl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // MARK: MenuBar
    JMenuBar menuBar = new JMenuBar();
    frmEspControl.setJMenuBar(menuBar);
    JButton btnNewConnection = new JButton("New Connection");
    menuBar.add(btnNewConnection);
    JButton btnDisconnect = new JButton("Disconnect");
    menuBar.add(btnDisconnect);
    Component horizontalGlue = Box.createHorizontalGlue();
    menuBar.add(horizontalGlue);
    JButton btnExit = new JButton("Exit");
    menuBar.add(btnExit);
    
    // MARK: CONTENT
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{0, 0, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
    gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0};
    gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    frmEspControl.getContentPane().setLayout(gridBagLayout);
    
    txtConnectedTo = new JTextField();
    txtConnectedTo.setEditable(false);
    txtConnectedTo.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
    txtConnectedTo.setText("Connected to xxx.xxx.x.x");
    GridBagConstraints gbc_txtConnectedTo = new GridBagConstraints();
    gbc_txtConnectedTo.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtConnectedTo.gridwidth = 2;
    gbc_txtConnectedTo.insets = new Insets(0, 0, 5, 5);
    gbc_txtConnectedTo.gridx = 0;
    gbc_txtConnectedTo.gridy = 0;
    frmEspControl.getContentPane().add(txtConnectedTo, gbc_txtConnectedTo);
    txtConnectedTo.setColumns(10);
    
    ip = new JTextField();
    ip.setToolTipText("0.0.0.0");
    GridBagConstraints gbc_ip = new GridBagConstraints();
    gbc_ip.insets = new Insets(0, 0, 5, 5);
    gbc_ip.fill = GridBagConstraints.HORIZONTAL;
    gbc_ip.gridx = 0;
    gbc_ip.gridy = 1;
    frmEspControl.getContentPane().add(ip, gbc_ip);
    ip.setColumns(10);
    
    JButton btnConnect = new JButton("Connect");
    btnConnect.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        
      }
    });
    GridBagConstraints gbc_btnConnect = new GridBagConstraints();
    gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
    gbc_btnConnect.gridx = 1;
    gbc_btnConnect.gridy = 1;
    frmEspControl.getContentPane().add(btnConnect, gbc_btnConnect);
    
    // MARK: Control buttons
    JButton btnUp = new JButton("Up");
    GridBagConstraints gbc_btnUp = new GridBagConstraints();
    gbc_btnUp.insets = new Insets(0, 0, 5, 5);
    gbc_btnUp.gridx = 1;
    gbc_btnUp.gridy = 2;
    frmEspControl.getContentPane().add(btnUp, gbc_btnUp);
    
    JButton btnLeft = new JButton("Left");
    GridBagConstraints gbc_btnLeft = new GridBagConstraints();
    gbc_btnLeft.anchor = GridBagConstraints.WEST;
    gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
    gbc_btnLeft.gridx = 0;
    gbc_btnLeft.gridy = 3;
    frmEspControl.getContentPane().add(btnLeft, gbc_btnLeft);
    
    JButton btnRight = new JButton("Right");
    GridBagConstraints gbc_btnRight = new GridBagConstraints();
    gbc_btnRight.insets = new Insets(0, 0, 5, 0);
    gbc_btnRight.gridx = 2;
    gbc_btnRight.gridy = 3;
    frmEspControl.getContentPane().add(btnRight, gbc_btnRight);
    
    JButton btnDown = new JButton("Down");
    GridBagConstraints gbc_btnDown = new GridBagConstraints();
    gbc_btnDown.insets = new Insets(0, 0, 5, 5);
    gbc_btnDown.gridx = 1;
    gbc_btnDown.gridy = 4;
    frmEspControl.getContentPane().add(btnDown, gbc_btnDown);
    
    // MARK: Debug text area
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    GridBagConstraints gbc_textArea = new GridBagConstraints();
    gbc_textArea.gridwidth = 3;
    gbc_textArea.fill = GridBagConstraints.BOTH;
    gbc_textArea.gridx = 0;
    gbc_textArea.gridy = 5;
    frmEspControl.getContentPane().add(textArea, gbc_textArea);
  }

} // End class

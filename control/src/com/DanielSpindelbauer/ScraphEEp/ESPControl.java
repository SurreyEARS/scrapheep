package com.DanielSpindelbauer.ScraphEEp;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

import com.DanielSpindelbauer.ScraphEEp.Comms;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;

public class ESPControl {

  private JFrame frame;
  
  private JButton btnConnect;
  private JButton btnDisconnect;
  private JButton btnExit;
  
  private JTextField txtIp;
  private JTextField txtConnectedTo;
  
  private JButton btnLForward;
  private JButton btnRBackward;
  private JButton btnLBackward;
  private JButton btnRForward;
  
  private static Comms comms = null;
  private JLabel lblLeft;
  private JLabel lblRight;
  private JButton btnStop;
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ESPControl window = new ESPControl();
          window.frame.setVisible(true);
        } catch (Exception e) {
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
    addListeners();
  }

  /**
   * Initialise the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setResizable(false);
    frame.setTitle("ESP Control");
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // MARK: - MenuBar
    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);
    
    btnDisconnect = new JButton("Disconnect");
    btnDisconnect.setEnabled(false);
    menuBar.add(btnDisconnect);
    Component horizontalGlue = Box.createHorizontalGlue();
    menuBar.add(horizontalGlue);
    btnExit = new JButton("Exit");
    menuBar.add(btnExit);
    
    // MARK: - CONTENT
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{1, 1};
    gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
    gridBagLayout.columnWeights = new double[]{1.0, 1.0};
    gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    frame.getContentPane().setLayout(gridBagLayout);
    
    txtConnectedTo = new JTextField();
    txtConnectedTo.setEditable(false);
    txtConnectedTo.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
    txtConnectedTo.setText("Waiting...");
    GridBagConstraints gbc_txtConnectedTo = new GridBagConstraints();
    gbc_txtConnectedTo.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtConnectedTo.gridwidth = 2;
    gbc_txtConnectedTo.insets = new Insets(0, 0, 5, 0);
    gbc_txtConnectedTo.gridx = 0;
    gbc_txtConnectedTo.gridy = 0;
    frame.getContentPane().add(txtConnectedTo, gbc_txtConnectedTo);
    txtConnectedTo.setColumns(10);
    
    // MARK: - IP input field 
    txtIp = new JTextField();
    txtIp.setText("192.168.0.102");
    txtIp.setToolTipText("0.0.0.0");
    GridBagConstraints gbc_ip = new GridBagConstraints();
    gbc_ip.insets = new Insets(0, 0, 5, 5);
    gbc_ip.fill = GridBagConstraints.HORIZONTAL;
    gbc_ip.gridx = 0;
    gbc_ip.gridy = 1;
    frame.getContentPane().add(txtIp, gbc_ip);
    txtIp.setColumns(10);
    
    btnConnect = new JButton("Connect");
    GridBagConstraints gbc_btnConnect = new GridBagConstraints();
    gbc_btnConnect.insets = new Insets(0, 0, 5, 0);
    gbc_btnConnect.gridx = 1;
    gbc_btnConnect.gridy = 1;
    frame.getContentPane().add(btnConnect, gbc_btnConnect);
    
    lblLeft = new JLabel("Left");
    GridBagConstraints gbc_lblLeft = new GridBagConstraints();
    gbc_lblLeft.insets = new Insets(0, 0, 5, 5);
    gbc_lblLeft.gridx = 0;
    gbc_lblLeft.gridy = 2;
    frame.getContentPane().add(lblLeft, gbc_lblLeft);
    
    lblRight = new JLabel("Right");
    GridBagConstraints gbc_lblRight = new GridBagConstraints();
    gbc_lblRight.insets = new Insets(0, 0, 5, 0);
    gbc_lblRight.gridx = 1;
    gbc_lblRight.gridy = 2;
    frame.getContentPane().add(lblRight, gbc_lblRight);
    
    // MARK: Control buttons
    btnLForward = new JButton("↑");
    GridBagConstraints gbc_btnLForward = new GridBagConstraints();
    gbc_btnLForward.insets = new Insets(0, 0, 5, 5);
    gbc_btnLForward.gridx = 0;
    gbc_btnLForward.gridy = 3;
    frame.getContentPane().add(btnLForward, gbc_btnLForward);
    
    btnRForward = new JButton("↑");
    GridBagConstraints gbc_btnRForward = new GridBagConstraints();
    gbc_btnRForward.insets = new Insets(0, 0, 5, 0);
    gbc_btnRForward.gridx = 1;
    gbc_btnRForward.gridy = 3;
    frame.getContentPane().add(btnRForward, gbc_btnRForward);
    
    btnLBackward = new JButton("↓");
    GridBagConstraints gbc_btnLBackward = new GridBagConstraints();
    gbc_btnLBackward.insets = new Insets(0, 0, 5, 5);
    gbc_btnLBackward.gridx = 0;
    gbc_btnLBackward.gridy = 4;
    frame.getContentPane().add(btnLBackward, gbc_btnLBackward);
    
    btnRBackward = new JButton("↓");
    GridBagConstraints gbc_btnRBackward = new GridBagConstraints();
    gbc_btnRBackward.insets = new Insets(0, 0, 5, 0);
    gbc_btnRBackward.gridx = 1;
    gbc_btnRBackward.gridy = 4;
    frame.getContentPane().add(btnRBackward, gbc_btnRBackward);
    
    btnStop = new JButton("Stop");
    GridBagConstraints gbc_btnStop = new GridBagConstraints();
    gbc_btnStop.gridwidth = 2;
    gbc_btnStop.insets = new Insets(0, 0, 5, 5);
    gbc_btnStop.gridx = 0;
    gbc_btnStop.gridy = 5;
    frame.getContentPane().add(btnStop, gbc_btnStop);
    
    // MARK: Debug text area
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    GridBagConstraints gbc_textArea = new GridBagConstraints();
    gbc_textArea.gridwidth = 2;
    gbc_textArea.fill = GridBagConstraints.BOTH;
    gbc_textArea.gridx = 0;
    gbc_textArea.gridy = 6;
    frame.getContentPane().add(textArea, gbc_textArea);
  }

  private void addListeners() {
    btnConnect.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionConnect();
      }
    });
    
    btnDisconnect.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
        comms.disconnect();
        txtIp.setEnabled(true);
        txtConnectedTo.setText("Waiting...");
      }
    });
    
    btnExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(comms != null) {
          if(comms.isConnected()) {
            comms.disconnect();
          }
        }
        frame.dispose();
      }
    });
    
    txtIp.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
          actionConnect();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
          actionConnect();
        }
      }
    });
    
    // listen for keys
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
      @Override
      public boolean dispatchKeyEvent(final KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_PRESSED) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
              actionLF();
              break;
            case KeyEvent.VK_E:
              actionRF();
              break;
            case KeyEvent.VK_A:
              actionLB();
              break;
            case KeyEvent.VK_D:
              actionRB();
              break;
            default:
          }
        } 
        if (e.getID() == KeyEvent.KEY_RELEASED) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
              actionReleaseLF();
              break;
            case KeyEvent.VK_E:
              actionReleaseRF();
              break;
            case KeyEvent.VK_A:
              actionReleaseLB();
              break;
            case KeyEvent.VK_D:
              actionReleaseRB();
              break;
            default:
          }
        }
        
        return false;
      }
    });
    
    btnLForward.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionLF();
      }
    });
    
    btnLBackward.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionLB();
      }
    });
    
    btnRForward.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionRF();
      }
    });
    
    btnRBackward.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        actionRB();
      }
    });
    
    btnStop.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        comms.stopValue();
      }
    });
  } // End addListener
  
  private void actionLF() {
    comms.setValue(0B0110); // Q
  }
  
  private void actionLB() {
    comms.setValue(0B0100); // A
  }
  
  private void actionRF() {
    comms.setValue(0B1000); // E
  }
  
  private void actionRB() {
    comms.setValue(0B1001); // D
  }
  
  private void actionReleaseLF() {
    comms.clearValue(0B0110); // Q
  }
  
  private void actionReleaseLB() {
    comms.clearValue(0B0100); // A
  }
  
  private void actionReleaseRF() {
    comms.clearValue(0B1000); // E
  }
  
  private void actionReleaseRB() {
    comms.clearValue(0B1001); // D
  }
  
  private void actionConnect() {
    if (!txtIp.getText().isEmpty()) { // check if ip is entered
      try {
        ESPControl.comms = new Comms(txtIp.getText()); // init comms
        
        comms.connect();
        btnDisconnect.setEnabled(true);
        btnConnect.setEnabled(false);
        txtIp.setEnabled(false);
        txtConnectedTo.setText("Connected to " + txtIp.getText());
      } catch(Exception err) {
//        err.printStackTrace();
        JOptionPane.showMessageDialog(null, err.getLocalizedMessage(), "Error when connecting", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
} // End class

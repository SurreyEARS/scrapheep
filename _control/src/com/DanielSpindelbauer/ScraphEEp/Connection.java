package com.DanielSpindelbauer.ScraphEEp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.DanielSpindelbauer.ScraphEEp.ConnectionStatus.ConnectionState;
import com.DanielSpindelbauer.ScraphEEp.ConnectionStatus.StateChangeListener;
import com.DanielSpindelbauer.ScraphEEp.utils.Utils;

public class Connection
{
	private InetAddress ip;
	private ConnectionListener connectionListener;
	private DatagramSocket socket;
	private Thread connectionThread;
	public DataPacket data;

	private ConnectionStatus status = new ConnectionStatus();

	public Connection()
	{
		try
		{
			socket = new DatagramSocket();
		}
		catch (SocketException e)
		{
			e.printStackTrace();
		}
		data = new DataPacket();
	}
	
	public void connect(String address)
	{
		if (status.getStatus() != ConnectionState.DISCONNECTED)
			return;
		
		try
		{
			if (!Utils.isIP(address))
			{
				try
				{
					int espId = Integer.parseInt(address);
					if (espId >= 0 && espId <= 12)
						address = String.format("192.168.1.1%02d", espId);
					else
						throw new Exception();
				}
				catch (Exception e)
				{
					throw new IllegalArgumentException("Address is invalid");
				}
			}
			ip = InetAddress.getByName(address);
			
			System.out.println("Connecting...");
			status.set(ConnectionState.CONNECTING);

			this.connectionListener = new ConnectionListener(this);
			this.connectionThread = new Thread(connectionListener);
			this.connectionThread.start();
		}
		catch (Exception e)
		{
			System.out.println("Error when connecting to ESP, check code (this is from Comms.connect())");
			status.set(ConnectionState.DISCONNECTED);
		}
	}

	/**
	 * Close connection to socket
	 */
	public void disconnect()
	{
		if (status.getStatus() == ConnectionState.DISCONNECTED)
			return;
		
		data.clearControls();
		try
		{
			sendData();
		}
		catch (IOException e)
		{
		}

		if (socket.isConnected())
			socket.close();

		if (connectionListener != null)
			connectionListener.stop();
		
		status.set(ConnectionState.DISCONNECTED);
		
		System.out.println("Disconnected");
	}

	private void sendData(byte[] buffer, int length) throws IOException
	{
		if (status.getStatus() == ConnectionState.DISCONNECTED)
			return;
		DatagramPacket sendPkt = new DatagramPacket(buffer, length, ip, 4210);
		try
		{
			socket.send(sendPkt);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	private void sendData() throws IOException
	{
		sendData(data.getValuesToSend(), data.getValuesToSend().length);
	}

	public String getIP()
	{
		if (ip == null)
			return "";
		return ip.getHostAddress();
	}

	private class ConnectionListener implements Runnable
	{
		private Connection comms;
		
		private boolean running = true;

		public ConnectionListener(Connection comms)
		{
			this.comms = comms;
		}

		public void run()
		{
			byte[] receiveData = new byte[1];
			try
			{
				sendData(new byte[1], 1);
				DatagramPacket reply = new DatagramPacket(receiveData, receiveData.length);
				comms.socket.setSoTimeout(5000);
				comms.socket.receive(reply);
			}
			catch (SocketTimeoutException e)
			{
				System.err.println("Timed out.");
				status.set(ConnectionState.DISCONNECTED);
				return;
			}
			catch (Exception e)
			{
				return;
			}
			if (receiveData[0] == 'R')
				System.out.println("Connection success!");
			else
				return;

			status.set(ConnectionState.CONNECTED);

			while (running)
			{
				try
				{
					Thread.sleep(10);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
					break;
				}
				if (data.hasChanged())
				{
					try
					{
						sendData();
						data.clearChanged();
					}
					catch (IOException e)
					{
					}
				}
			}
		}
		
		public void stop()
		{
			running = false;
		}
	}

	public boolean isConnected()
	{
		return status.getStatus() == ConnectionState.CONNECTED;
	}
	
	public void addStatusListener(StateChangeListener listener)
	{
		status.addListener(listener);
	}
}

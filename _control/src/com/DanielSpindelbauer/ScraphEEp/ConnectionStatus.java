package com.DanielSpindelbauer.ScraphEEp;

import java.util.ArrayList;

public class ConnectionStatus
{
	public enum ConnectionState
	{
		CONNECTED, CONNECTING, DISCONNECTED;
	}
	
	private ConnectionState state = ConnectionState.DISCONNECTED;
	private static ArrayList<StateChangeListener> listeners = new ArrayList<>();
	
	public void set(ConnectionState status)
	{
		state = status;
		for (StateChangeListener listener : listeners)
			listener.changed(state);
	}
	
	public ConnectionState getStatus()
	{
		return state;
	}
	
	public void addListener(StateChangeListener listener)
	{
		listeners.add(listener);
	}
	
	public interface StateChangeListener
	{
		public void changed(ConnectionState status);
	}
}

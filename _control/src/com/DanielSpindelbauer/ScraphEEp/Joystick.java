/**
 * Joystick.java
 */
package com.DanielSpindelbauer.ScraphEEp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SimpleJoystick extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int joyOutputRange;
	private int joyRadius;
	private int joyCenterX, joyCenterY;
	public Point position = new Point();

	/**
	 * Constructor. Set field values.
	 *
	 * @param joyOutputRange
	 */
	public SimpleJoystick(final int joyOutputRange)
	{
		this.joyOutputRange = joyOutputRange;
		setBackground(new Color(226, 226, 226));
		MouseAdapter mouseAdapter = new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				if (isEnabled() && SwingUtilities.isLeftMouseButton(e) && cursorChanged(e.getX(), e.getY()))
				{
					SwingUtilities.getRoot(SimpleJoystick.this).repaint();
					fireStateChanged();
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (isEnabled() && SwingUtilities.isLeftMouseButton(e) && cursorChanged(e.getX(), e.getY()))
				{
					SwingUtilities.getRoot(SimpleJoystick.this).repaint();
					fireStateChanged();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (isEnabled() && SwingUtilities.isLeftMouseButton(e) && cursorChanged(e.getX(), e.getY()))
				{
					SwingUtilities.getRoot(SimpleJoystick.this).repaint();
					cursorChanged(joyCenterX, joyCenterY);
					fireStateChanged();
				}
			}
		};
		addMouseMotionListener(mouseAdapter);
		addMouseListener(mouseAdapter);
	}

	private boolean cursorChanged(int mouseX, int mouseY)
	{
		if (joyRadius == 0)
			return false;

		position.x = mouseX - joyCenterX;
		position.y = mouseY - joyCenterY;

		// Added by Phil
		double dist = Math.sqrt(position.x * position.x + position.y * position.y);
		if (dist > joyRadius)
		{
			position.x *= (double) joyRadius / dist;
			position.y *= (double) joyRadius / dist;
		}

		position.x = (int) Math.max(Math.min(joyOutputRange * (double) position.x / (double) joyRadius, 127), -127);
		position.y = (int) Math.max(Math.min(-joyOutputRange * (double) position.y / (double) joyRadius, 127), -127);

		return true;
	}

	@Override
	protected void paintComponent(final Graphics g)
	{
		super.paintComponent(g);
		int joyWidth = getSize().width;
		int joyHeight = getSize().height;
		joyRadius = Math.min(joyWidth, joyHeight) / 2 - 20;
		if (joyRadius == 0)
			return;

		joyCenterX = joyWidth / 2;
		joyCenterY = joyHeight / 2;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int diameter;

		// background
		g2.setColor(isEnabled() ? Color.LIGHT_GRAY : Color.GRAY);
		diameter = joyRadius * 2;
		g2.fillOval(joyCenterX - diameter / 2, joyCenterY - diameter / 2, diameter, diameter);

		g2.setColor(isEnabled() ? Color.RED : new Color(0xA00000));
		diameter = 40;
		g2.fillOval(joyCenterX + position.x - diameter / 2, joyCenterY - position.y - diameter / 2, diameter, diameter);

		// thumb
		g2.setColor(isEnabled() ? Color.GRAY : Color.DARK_GRAY);
		diameter = 20;
		g2.fillOval(joyCenterX - diameter / 2,  joyCenterY - diameter / 2, diameter, diameter);
	}

	void addChangeListener(ChangeListener listener)
	{
		listenerList.add(ChangeListener.class, listener);
	}

	void removeChangeListener(ChangeListener listener)
	{
		listenerList.remove(ChangeListener.class, listener);
	}

	protected void fireStateChanged()
	{
		ChangeEvent e = new PointChangeEvent(this, position);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == ChangeListener.class)
			{
				((ChangeListener) listeners[i + 1]).stateChanged(e);
			}
		}
	}
}

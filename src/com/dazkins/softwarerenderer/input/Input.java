package com.dazkins.softwarerenderer.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{
	private boolean keys[];
	
	public Input()
	{
		keys = new boolean[256];
	}
	
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public boolean isKeyDown(int k)
	{
		return keys[k];
	}
}
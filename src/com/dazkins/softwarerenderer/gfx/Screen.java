package com.dazkins.softwarerenderer.gfx;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Screen
{
	private JFrame frame;
	
	private Bitmap bmp;
	private int width;
	private int height;
	
	private int pixelScale;
	
	private BufferedImage scrImage;
	
	public Screen(int w, int h, int ps)
	{
		this.width = w;
		this.height = h;
		this.scrImage = new BufferedImage(w / ps, h / ps, BufferedImage.TYPE_INT_RGB);
		this.bmp = new Bitmap(scrImage);
		
		this.frame = new JFrame();
		
		this.pixelScale = ps;
		
		Dimension d = new Dimension(width, height);
		frame.setPreferredSize(d);
		frame.setMinimumSize(d);
		frame.setMaximumSize(d);
		
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	public void attachKeyListener(KeyListener k)
	{
		frame.addKeyListener(k);
	}
	
	public void updateGraphics()
	{
		BufferStrategy bs = frame.getBufferStrategy();
		
		if (bs == null)
		{
			frame.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(scrImage, 0, 0, width, height, null);
		
		g.dispose();
		bs.show();
		
		bmp.clear(0);
	}
	
	public Bitmap getBitmap()
	{
		return bmp;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getPWidth()
	{
		return bmp.getWidth();
	}
	
	public int getPHeight()
	{
		return bmp.getHeight();
	}
}
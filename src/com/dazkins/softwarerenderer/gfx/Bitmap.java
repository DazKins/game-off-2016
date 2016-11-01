package com.dazkins.softwarerenderer.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Bitmap
{
	private int width;
	private int height;
	private int[] pixels;
	
	public Bitmap(int w, int h)
	{
		this.width = w;
		this.height = h;
		this.pixels = new int[w * h];
	}
	
	public Bitmap(BufferedImage img)
	{
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void clear(int cc)
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				pixels[x + y * width] = cc;
			}
		}
	}
	
	public void randomize()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				pixels[x + y * width] = (int) (Math.random() * 0xFFFFFF);
			}
		}
	}
	
	public void setPixel(int x, int y, int col)
	{
		if (x < 0 || y < 0 || x >= width || y >= height) return;
		pixels[x + y * width] = col;
	}
	
	public void setPixel(int x, int y, Color col)
	{
		if (x < 0 || y < 0 || x >= width || y >= height) return;
		pixels[x + y * width] = col.getInt();
	}
}
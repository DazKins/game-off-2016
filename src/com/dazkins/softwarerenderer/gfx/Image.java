package com.dazkins.softwarerenderer.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	private int pixels[];
	private int width;
	private int height;
	
	public Image(String p)
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(Image.class.getResource(p));
			
			this.width = img.getWidth();
			this.height = img.getHeight();
			pixels = img.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getPixel(int x, int y)
	{
		return pixels[x + y * width];
	}
}
package com.dazkins.softwarerenderer.gfx;

public class Color
{
	public static final Color WHITE = new Color(255, 255, 255);
	
	private int color;
	
	public Color(int c)
	{
		this.color = c;
	}
	
	public Color(int r, int g, int b)
	{
		this.color = ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
	}
	
	public int getInt()
	{
		return color;
	}
	
	public Color clone()
	{
		return new Color(color);
	}
	
	public int getR()
	{
		return (color >> 16) & 0xFF;
	}
	
	public int getG()
	{
		return (color >> 8) & 0xFF;
	}
	
	public int getB()
	{
		return color & 0xFF;
	}
	
	public void setR(int r)
	{
		int ar = r & 0xFF;
		
		int nc = color & 0x00FFFF;
		
		this.color = nc | (ar << 16);
	}
	
	public void setG(int g)
	{
		int ag = g & 0xFF;
		
		int nc = color & 0xFF00FF;
		
		this.color = nc | (ag << 8);
	}
	
	public void setB(int b)
	{
		int ag = b & 0xFF;
		
		int nc = color & 0xFFFF00;
		
		this.color = nc | ag;
	}
	
	public static Color mult(Color c1, float v)
	{
		int r = c1.getR();
		int g = c1.getG();
		int b = c1.getB();
		
		int ir = (int) (r * v);
		int ig = (int) (g * v);
		int ib = (int) (b * v);
		
		return new Color(ir, ig, ib);
	}
	
	public static Color add(Color...cs)
	{
		int tr = 0;
		int tg = 0;
		int tb = 0;
		for (int i = 0; i < cs.length; i++)
		{
			tr += cs[i].getR();
			tg += cs[i].getG();
			tb += cs[i].getB();
		}
		Color c = new Color(0);
		c.setR(tr);
		c.setG(tg);
		c.setB(tb);
		return c;
	}
	
	//when v = 0, lerp = c1, when v = 1, lerp = c2
	public static Color lerp(Color c1, Color c2, float v)
	{
		int r1 = c1.getR();
		int g1 = c1.getG();
		int b1 = c1.getB();

		int r2 = c2.getR();
		int g2 = c2.getG();
		int b2 = c2.getB();
		
		int nr = (int) ((1 - v) * r1 + v * r2);
		int ng = (int) ((1 - v) * g1 + v * g2);
		int nb = (int) ((1 - v) * b1 + v * b2);
		
		return new Color(nr, ng, nb);
	}
	
	public boolean equals(Color c)
	{
		return c.color == color;
	}
}
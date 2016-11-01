package com.dazkins.softwarerenderer.gfx;

public class RenderContext2D
{
	private ScanBuffer2D scanBuffer;
	private int height;
	
	private int lineCount;
	
	private Bitmap bitmap;
	
	public RenderContext2D(Bitmap b)
	{
		this.height = b.getHeight();
		this.bitmap = b;
		this.scanBuffer = new ScanBuffer2D(this.height, bitmap.getWidth());
	}
	
	public ScanBuffer2D getScanBuffer()
	{
		return scanBuffer;
	}
	
	public void scanLine(int x0, int y0, float z0, int x1, int y1, float z1)
	{
		if (y0 > y1)
		{
			int tmp = y0;
			y0 = y1;
			y1 = tmp;
			
			tmp = x0;
			x0 = x1;
			x1 = tmp;
		}
		
		for (int y = y0; y < y1; y++)
		{
			float perc = ((float) y - y0) / (y1 - y0);
			
			int x = (int) ((x1 - x0) * perc + x0);
			
			float z = (z1 - z0) * perc + z0;
			scanBuffer.scan(y, x, x, z);
		}
		
		lineCount++;
		
		if (lineCount == 3)
		{
			scanBuffer.renderScanToBitmap(bitmap);
			lineCount = 0;
		}
	}
}
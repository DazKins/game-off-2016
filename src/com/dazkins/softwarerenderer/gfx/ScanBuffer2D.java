package com.dazkins.softwarerenderer.gfx;

import com.dazkins.softwarerenderer.math.MathHelper;

public class ScanBuffer2D
{
	private int scan[];
	
	private float localDepthScanBuffer[];
	
	private float globalDepthBuffer[];
	
	private int height;
	
	private int maxWidth;
	
	private Color color0;
	private int colorPos0x;
	private int colorPos0y;
	
	private Color color1;
	private int colorPos1x;
	private int colorPos1y;
	
	private Color color2;
	private int colorPos2x;
	private int colorPos2y;
	
	public ScanBuffer2D(int h, int w)
	{
		this.height = h;
		scan = new int[h * 2];
		localDepthScanBuffer = new float[h * 2];
		this.maxWidth = w;
		globalDepthBuffer = new float[h * maxWidth];
		purgeDepthBuffer();
		
		clear();
		clearColors();
	}
	
	public void registerColors(int x0, int y0, Color c0, int x1, int y1, Color c1, int x2, int y2, Color c2)
	{
		color0 = c0.clone();
		colorPos0x = x0;
		colorPos0y = y0;

		color1 = c1.clone();
		colorPos1x = x1;
		colorPos1y = y1;

		color2 = c2.clone();
		colorPos2x = x2;
		colorPos2y = y2;
	}
	
	public void clearColors()
	{
		color0 = Color.WHITE.clone();
		colorPos0x = 0;
		colorPos0y = 0;

		color1 = Color.WHITE.clone();
		colorPos1x = 0;
		colorPos1y = 0;

		color2 = Color.WHITE.clone();
		colorPos2x = 0;
		colorPos2y = 0;
	}
	
	public void scan(int y, int xMin, int xMax, float z)
	{
		if (y < 0 || y >= height)
		{
			return;
		}
		
		if (getScanMin(y) > xMin)
		{
			setScanMin(y, xMin);
			localDepthScanBuffer[y * 2 + 0] = z;
		}

		if (getScanMax(y) < xMax)
		{
			setScanMax(y, xMax);
			localDepthScanBuffer[y * 2 + 1] = z;
		}
	}
	
	public void renderScanToBitmap(Bitmap b)
	{
		if (b.getHeight() != height)
		{
			System.err.println("Bitmap is not same height as scan buffer!");
			return;
		}
		
		for (int y = 0; y < height; y++)
		{
			int xMin = getScanMin(y);
			int xMax = getScanMax(y);
			
			float z0 = localDepthScanBuffer[y * 2 + 0];
			float z1 = localDepthScanBuffer[y * 2 + 1];
			
			int iXMin = xMin;
			int iXMax = xMax;
			
			if (iXMin < 0) iXMin = 0;
			if (iXMin >= maxWidth) iXMin = maxWidth - 1;
			
			if (iXMax < 0) iXMax = 0;
			if (iXMax >= maxWidth) iXMax = maxWidth - 1;
			
			for (int x = iXMin; x <= iXMax; x++)
			{
				float perc = ((float) x - xMin) / (xMax - xMin);
				
				float z = (z1 - z0) * perc + z0;
				
				if (z < globalDepthBuffer[x + y * height] && z > 0)
				{
					Color fCol = color0.clone();
					if (!(color0.equals(color1) && color1.equals(color2)))
					{
						int dx0 = x - colorPos0x;
						int dy0 = y - colorPos0y;
	//					float d0 = (float) Math.sqrt(dx0 * dx0 + dy0 * dy0);
						float d0 = (float) MathHelper.fastSqrt(dx0 * dx0 + dy0 * dy0, 1);
	//					float d0 = dx0 * dx0 + dy0 * dy0;
	
						int dx1 = x - colorPos1x;
						int dy1 = y - colorPos1y;
						float d1 = (float) MathHelper.fastSqrt(dx1 * dx1 + dy1 * dy1, 1);
	//					float d1 = dx1 * dx1 + dy1 * dy1;
	
						int dx2 = x - colorPos2x;
						int dy2 = y - colorPos2y;
						float d2 = (float) MathHelper.fastSqrt(dx2 * dx2 + dy2 * dy2, 1);
	//					float d2 = dx2 * dx2 + dy2 * dy2;
						
						float denom = d0 * d1 + d0 * d2 + d1 * d2;
						
						float lc0 = (d2 * d1) / denom;
						float lc1 = (d0 * d2) / denom;
						float lc2 = (d1 * d0) / denom;
						
						Color lcol0 = Color.mult(color0, lc0);
						Color lcol1 = Color.mult(color1, lc1);
						Color lcol2 = Color.mult(color2, lc2);
						
						fCol = Color.add(lcol0, lcol1, lcol2);
					}
//					b.setPixel(x, y, (int) (z*4 + 20));
					b.setPixel(x, y, fCol);
					globalDepthBuffer[x + y * height] = z;
				}
			}
		}
		clear();
		clearColors();
	}
	
	public void purgeDepthBuffer()
	{
		for (int i = 0; i < height * maxWidth; i++)
		{
			globalDepthBuffer[i] = Float.MAX_VALUE;
		}
	}
	
	public void clear()
	{
		for (int i = 0; i < this.height; i++)
		{
			scan[i * 2 + 0] = Integer.MAX_VALUE;
			scan[i * 2 + 1] = Integer.MIN_VALUE;
		}
	}
	
	private int getScanMin(int y)
	{
		if (y < 0 || y >= height) return -1;
		return scan[y * 2 + 0];
	}
	
	private int getScanMax(int y)
	{
		if (y < 0 || y >= height) return -1;
		return scan[y * 2 + 1];
	}
	
	private void setScanMin(int y, int x)
	{
		if (y < 0 || y >= height) return;
		scan[y * 2 + 0] = x;
	}
	
	private void setScanMax(int y, int x)
	{
		if (y < 0 || y >= height) return;
		scan[y * 2 + 1] = x;
	}
}
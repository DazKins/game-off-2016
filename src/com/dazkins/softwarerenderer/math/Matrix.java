package com.dazkins.softwarerenderer.math;

public class Matrix
{
	protected float values[][];
	protected int width;
	protected int height;
	
	public static Matrix generateMatrix(int w, int h)
	{
		Matrix m = new Matrix();
		m.values = new float[w][h];
		m.width = w;
		m.height = h;
		return m;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setValue(int x, int y, float v)
	{
		values[x][y] = v;
	}
	
	public float getValue(int x, int y)
	{
		return values[x][y];
	}
	
	public static Matrix generateIdentityMatrix(int s)
	{
		Matrix r = generateMatrix(s, s);
		for (int x = 0; x < s; x++)
		{
			for (int y = 0; y < s; y++)
			{
				if (x == y)
					r.setValue(x, y, 1.0f);
			}
		}
		return r;
	}
	
	public static Matrix generatePerspectiveProjectionMatrix(float f, float zn, float zf)
	{
		Matrix m = Matrix.generateIdentityMatrix(4);
		
		float s = (float) (1.0f / (Math.tan(f / 2.0f)));
		float fn = zf = zn;
		
		m.setValue(0, 0, 100);
		m.setValue(1, 1, 100);
//		m.setValue(2, 2, zf / fn);
//		m.setValue(2, 3, (zn * zf) / fn);
//		m.setValue(3, 2, 1);
		
		m.setValue(2, 3, 0.2f);
		
		return m;
	}
	
	public static Matrix generateTranslationMatrix(float x, float y, float z)
	{
		Matrix m = Matrix.generateIdentityMatrix(4);
		
		m.setValue(3, 0, x);
		m.setValue(3, 1, y);
		m.setValue(3, 2, z);
		
		return m;
	}
	
	public static Matrix generateXRotationMatrix(float theta)
	{
		Matrix m = Matrix.generateIdentityMatrix(4);
		
		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		 
		m.setValue(1, 1, cos);
		m.setValue(2, 1, -sin);
		m.setValue(1, 2, sin);
		m.setValue(2, 2, cos);
		
		return m;
	}
	
	public static Matrix generateYRotationMatrix(float theta)
	{
		Matrix m = Matrix.generateIdentityMatrix(4);
		
		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		 
		m.setValue(0, 0, cos);
		m.setValue(0, 2, -sin);
		m.setValue(2, 0, sin);
		m.setValue(2, 2, cos);
		
		return m;
	}
	
	public static Matrix generateZRotationMatrix(float theta)
	{
		Matrix m = Matrix.generateIdentityMatrix(4);
		
		float cos = (float) Math.cos(theta);
		float sin = (float) Math.sin(theta);
		 
		m.setValue(0, 0, cos);
		m.setValue(1, 0, -sin);
		m.setValue(0, 1, sin);
		m.setValue(1, 1, cos);
		
		return m;
	}
	
	public static Matrix multiply(Matrix m0, Matrix m1)
	{
		if (m0.width != m1.height)
		{
			System.err.println("These matrices cannot be multiplied");
		}
		
		Matrix result = generateMatrix(m1.width, m0.height);
		
		for (int i = 0; i < m0.height; i++)
		{
			for (int u = 0; u < m1.width; u++)
			{
				float tot = 0;
				for (int p = 0; p < m0.width; p++)
				{
					tot += m0.getValue(p, i) * m1.getValue(u, p);
				}
				result.setValue(u, i, tot);
			}
		}
		
		return result;
	}
	
	public String toString()
	{
		String r = "[ ";
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (x == width - 1 && y == height - 1)
					r += values[x][y] + " ]";
				else
					r += values[x][y] + ", ";
			}
			r += System.getProperty("line.separator") + "  ";
		}
		return r;
	}
}
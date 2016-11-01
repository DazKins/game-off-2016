package com.dazkins.softwarerenderer.math;

public abstract class Vector extends Matrix
{
	public static Matrix generateVector(int h)
	{
		Matrix m = new Matrix();
		m.values = new float[1][h];
		m.width = 1;
		m.height = h;
		m.setValue(0, h - 1, 1.0f);
		return m;
	}
	
	public static Matrix generateVector(float... vals)
	{
		Matrix m = generateVector(vals.length + 1);
		
		for (int i = 0; i < vals.length; i++)
		{
			m.setValue(0, i, vals[i]);
		}
		
		return m;
	}
}
package com.dazkins.softwarerenderer.math;

public class MathHelper
{
	public static float fastSqrt(float a, int iterations)
	{
		float tot = 1;
		for (int i = 0; i < iterations; i++)
		{
			tot = 0.5f * (tot + a / tot);
		}
		return tot;
	}
}
package com.dazkins.softwarerenderer.gfx;

import com.dazkins.softwarerenderer.math.Matrix;

public class RenderContext3D
{
	private GeometryRenderer2D geometryRenderer2D;
	private Screen screen;
	
	private Matrix currentMatrix;
	private Matrix perspectiveProjMatrix;
	
	private Matrix[] curVectors;
	
	private Color[] curColors;
	private Color curColor;
	
	private int curVectorIndex;
	
	private float fov;
	private float zNear;
	private float zFar;
	
	public RenderContext3D(GeometryRenderer2D g, Screen s, float f, int zn, int zf)
	{
		this.geometryRenderer2D = g;
		this.currentMatrix = Matrix.generateIdentityMatrix(4);
		this.screen = s;
		
		this.curVectors = new Matrix[3];
		this.curColors = new Color[3];
		this.curColor = Color.WHITE.clone();
		
		this.fov = f;
		this.zNear = zn;
		this.zFar = zf;
		
		this.perspectiveProjMatrix = Matrix.generatePerspectiveProjectionMatrix(f, zn, zf);
	}
	
	public void multiplyMatrix(Matrix m)
	{
		currentMatrix = Matrix.multiply(currentMatrix, m);
	}
	
	public void setIdentityMatrix()
	{
		currentMatrix = Matrix.generateIdentityMatrix(4);
	}
	
	public void setColor(Color c)
	{
		curColor = c;
	}
	
	public void pushVector(Matrix m)
	{
		if (m.getWidth() != 1)
		{
			return;
		}
		
		if (curVectorIndex >= 3)
		{
			Color c0 = curColors[0];
			Color c1 = curColors[1];
			Color c2 = curColors[2];
			
			Matrix vect0 = Matrix.multiply(currentMatrix, curVectors[0]);
			Matrix vect1 = Matrix.multiply(currentMatrix, curVectors[1]);
			Matrix vect2 = Matrix.multiply(currentMatrix, curVectors[2]);
			
			Matrix vecp0 = Matrix.multiply(perspectiveProjMatrix, vect0);
			Matrix vecp1 = Matrix.multiply(perspectiveProjMatrix, vect1);
			Matrix vecp2 = Matrix.multiply(perspectiveProjMatrix, vect2);
			
			geometryRenderer2D.drawTriangle((vecp0.getValue(0, 0) / vecp0.getValue(0, 3)) + screen.getPWidth() / 2, (vecp0.getValue(0, 1) / vecp0.getValue(0, 3)) + screen.getPHeight() / 2, vecp0.getValue(0, 2), c0,
											(vecp1.getValue(0, 0) / vecp1.getValue(0, 3)) + screen.getPWidth() / 2, (vecp1.getValue(0, 1) / vecp1.getValue(0, 3)) + screen.getPHeight() / 2, vecp1.getValue(0, 2), c1,
											(vecp2.getValue(0, 0) / vecp2.getValue(0, 3)) + screen.getPWidth() / 2, (vecp2.getValue(0, 1) / vecp2.getValue(0, 3)) + screen.getPHeight() / 2, vecp2.getValue(0, 2), c2);
			
			curVectors = new Matrix[3];
			curVectorIndex = 0;
		}

		curColors[curVectorIndex] = curColor.clone();
		curVectors[curVectorIndex++] = m;
		
		curColor = Color.WHITE.clone();
	}
}
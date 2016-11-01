package com.dazkins.softwarerenderer.gfx;

import java.awt.event.KeyEvent;

import com.dazkins.softwarerenderer.input.Input;
import com.dazkins.softwarerenderer.math.Matrix;

public class Camera
{
	private float x;
	private float y;
	private float z;
	
	private float xRot;
	private float yRot;
	
	public void updateWithInputHandler(Input i)
	{
		if (i.isKeyDown(KeyEvent.VK_A))
		{
			x -= Math.cos(yRot);
			z += Math.sin(yRot);
		}
		if (i.isKeyDown(KeyEvent.VK_D))
		{
			x += Math.cos(yRot);
			z -= Math.sin(yRot);
		}
		
		if (i.isKeyDown(KeyEvent.VK_W))
		{
			z += Math.cos(yRot);
			x += Math.sin(yRot);
			
			y -= Math.sin(xRot);
		}
		if (i.isKeyDown(KeyEvent.VK_S))
		{
			z -= Math.cos(yRot);
			x -= Math.sin(yRot);

			y += Math.sin(xRot);
		}
		
		if (i.isKeyDown(KeyEvent.VK_LEFT))
		{
			yRot -= 0.01f;
		}
		if (i.isKeyDown(KeyEvent.VK_RIGHT))
		{
			yRot += 0.01f;
		}
		
		if (i.isKeyDown(KeyEvent.VK_UP))
		{
			xRot += 0.01f;
		}
		if (i.isKeyDown(KeyEvent.VK_DOWN))
		{
			xRot -= 0.01f;
		}
	}
	
	public Matrix getTranslationMatirx()
	{
		Matrix m = Matrix.generateIdentityMatrix(4);

		Matrix m0 = Matrix.multiply(m, Matrix.generateXRotationMatrix(-xRot));
		Matrix m1 = Matrix.multiply(m0, Matrix.generateYRotationMatrix(-yRot));
		Matrix m2 = Matrix.multiply(m1, Matrix.generateTranslationMatrix(-x, -y, -z));
		
		return m2;
	}
}
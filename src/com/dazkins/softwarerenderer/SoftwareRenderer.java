package com.dazkins.softwarerenderer;

import com.dazkins.softwarerenderer.gfx.Camera;
import com.dazkins.softwarerenderer.gfx.Color;
import com.dazkins.softwarerenderer.gfx.GeometryRenderer2D;
import com.dazkins.softwarerenderer.gfx.RenderContext2D;
import com.dazkins.softwarerenderer.gfx.RenderContext3D;
import com.dazkins.softwarerenderer.gfx.Screen;
import com.dazkins.softwarerenderer.input.Input;
import com.dazkins.softwarerenderer.math.MathHelper;
import com.dazkins.softwarerenderer.math.Matrix;
import com.dazkins.softwarerenderer.math.Vector;

public class SoftwareRenderer implements Runnable
{
	private Screen screen;
	private boolean running;
	
	private RenderContext2D renderContext2D;
	private GeometryRenderer2D geometryRenderer2D;
	
	private RenderContext3D renderContext3D;
	
	private Input input;
	private Camera camera;
	
	public static void main(String args[])
	{
		new SoftwareRenderer().run();
	}
	
	public SoftwareRenderer()
	{
		screen = new Screen(1000, 1000, 5);
		renderContext2D = new RenderContext2D(screen.getBitmap());
		geometryRenderer2D = new GeometryRenderer2D(renderContext2D);
		renderContext3D = new RenderContext3D(geometryRenderer2D, screen, (float) Math.PI / 2.0f, 1, 10);
		input = new Input();
		screen.attachKeyListener(input);
		camera = new Camera();
	}

	public void run()
	{
		screen.show();
		this.start();
	}
	
	public void start()
	{
		this.running = true;
		this.mainLoop();
	}
	
	private void mainLoop()
	{
		double tickPerSecond = 60;
		double nsPerTick = 1000000000.0 / tickPerSecond;
		long lastTime = System.nanoTime();
		double delta = 0.0;
		long lastTickUpdate = System.currentTimeMillis();
		int fps = 0;
		int ups = 0;
		
		while (running)
		{
			long thisTime = System.nanoTime();
			long timeDiff = thisTime - lastTime;
			lastTime = thisTime;
			
			double deltaPlus = timeDiff / nsPerTick;
			delta += deltaPlus;
			while (delta >= 1)
			{
				tick();
				delta--;
				ups++;
				render();
				fps++;
			}
			
			if (System.currentTimeMillis() - lastTickUpdate >= 1000)
			{
				lastTickUpdate = System.currentTimeMillis();
				System.out.println("FPS: " + fps + " UPS: " + ups);
				fps = 0;
				ups = 0;
			}
		}
	}
	
	private float t = 0f;
	
	public void render()
	{
		renderContext3D.setIdentityMatrix();
		
		Matrix camMat = camera.getTranslationMatirx();
		renderContext3D.multiplyMatrix(camMat);
//		
		renderContext3D.multiplyMatrix(Matrix.generateTranslationMatrix(0.0f, 0.0f, 20.0f));
//		renderContext3D.multiplyMatrix(Matrix.generateYRotationMatrix(t));
		
		Matrix m0 = Vector.generateVector(-10.0f, -10.0f, 0.0f);
		Matrix m1 = Vector.generateVector(10.0f, -10.0f, 0.0f);
		Matrix m2 = Vector.generateVector(0.0f, 0.0f, 0.0f);

		renderContext3D.setColor(new Color(0, 255, 0));
		renderContext3D.pushVector(m0);
		renderContext3D.setColor(new Color(255, 0, 0));
		renderContext3D.pushVector(m1);
		renderContext3D.setColor(new Color(255, 0, 0));
		renderContext3D.pushVector(m2);

		renderContext3D.multiplyMatrix(Matrix.generateTranslationMatrix(0.0f, 0.0f, 20.0f));
//		renderContext3D.multiplyMatrix(Matrix.generateYRotationMatrix(t));

		Matrix m3 = Vector.generateVector(-10.0f, -50.0f, 0.0f);
		Matrix m4 = Vector.generateVector(10.0f, -70.0f, 0.0f);
		Matrix m5 = Vector.generateVector(0.0f, -10.0f, 0.0f);

		renderContext3D.setColor(new Color(255, 0, 0));
		renderContext3D.pushVector(m3);
		renderContext3D.setColor(new Color(0, 255, 0));
		renderContext3D.pushVector(m4);
		renderContext3D.setColor(new Color(0, 0, 255));
		renderContext3D.pushVector(m5);
		
		renderContext3D.multiplyMatrix(Matrix.generateTranslationMatrix(0.0f, 0.0f, 20.0f));
//		renderContext3D.multiplyMatrix(Matrix.generateYRotationMatrix(t));

		
		Matrix m6 = Vector.generateVector(10.0f, -50.0f, 0.0f);
		Matrix m7 = Vector.generateVector(10.0f, 40.0f, 0.0f);
		Matrix m8 = Vector.generateVector(5.0f, -10.0f, 0.0f);

		renderContext3D.setColor(new Color(0, 255, 255));
		renderContext3D.pushVector(m6);
		renderContext3D.setColor(new Color(0, 0, 0));
		renderContext3D.pushVector(m7);
		renderContext3D.setColor(new Color(255, 0, 255));
		renderContext3D.pushVector(m8);
		
		t -= 0.1f;
		
//		Matrix m3 = Vector.generateVector(0.0f, 0.0f, 0.0f);
//		Matrix m4 = Vector.generateVector(100.0f, -100.0f, 1000.0f);
//		Matrix m5 = Vector.generateVector(100.0f, 100.0f, 1000.0f);
//		
//		renderContext3D.pushVector(m3);
//		renderContext3D.pushVector(m4);
//		renderContext3D.pushVector(m5);
		
		renderContext2D.getScanBuffer().purgeDepthBuffer();
		screen.updateGraphics();
	}
	
	public void tick()
	{
		camera.updateWithInputHandler(input);
	}
}
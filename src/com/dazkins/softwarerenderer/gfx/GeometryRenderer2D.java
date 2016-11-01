package com.dazkins.softwarerenderer.gfx;

public class GeometryRenderer2D
{
	private RenderContext2D renderContext;
	
	public GeometryRenderer2D(RenderContext2D r)
	{
		this.renderContext = r;
	}
	
	public void drawTriangle(float x0, float y0, float z0, Color c0, float x1, float y1, float z1, Color c1, float x2, float y2, float z2, Color c2)
	{
		renderContext.getScanBuffer().registerColors((int) x0, (int) y0, c0, (int) x1, (int) y1, c1, (int) x2, (int) y2, c2);
		renderContext.scanLine((int) x0, (int) y0, z0, (int) x1, (int) y1, z1);
		renderContext.scanLine((int) x1, (int) y1, z1, (int) x2, (int) y2, z2);
		renderContext.scanLine((int) x2, (int) y2, z2, (int) x0, (int) y0, z0);
		renderContext.getScanBuffer().clearColors();
	}
}
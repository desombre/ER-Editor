package model;
/**
  * EReditor
  * model.Relationship.java
  * Created by Palle on 30.05.2014
  * Copyright (c) 2014 - 2017 Palle
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in
  * all copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  * THE SOFTWARE.
  */

import main.ER_Editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class Relationship extends ERObject
{

	private static final long serialVersionUID = 1L;
	
	private Entity	e1;
	private Entity	e2;
	private boolean	e1toMany;
	private boolean	e2toMany;
	
	public Relationship()
	{
		getBounds().x = 400;
		getBounds().y = 200;
		getBounds().width = 200;
		getBounds().height = 80;
		setName(ER_Editor.getLOCALIZATION().getString("relationship_default_name"));
	}
	
	public Relationship(Entity e1, Entity e2, boolean e1toMany, boolean e2toMany, String name) {
		this.e1 = e1;
		this.e2 = e2;
		this.e1toMany = e1toMany;
		this.e2toMany = e2toMany;
		this.setName(name);
	}

	public Entity getFirstEntity()
	{
		return e1;
	}
	
	public boolean getFirstEntityToMany()
	{
		return e1toMany;
	}
	
	public Entity getSecondEntity()
	{
		return e2;
	}
	
	public boolean getSecondEntityToMany()
	{
		return e2toMany;
	}
	
	@Override
	public void paint(Graphics2D g)
	{
		getBounds().width = isWeak() ? 210 : 200;
		getBounds().height = isWeak() ? 90 : 80;
		
		if (isSelected())
			g.setColor(new Color(0, 150, 180));
		else
			g.setColor(Color.BLACK);
			
		int e1shiftX = (int) ((e1.getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
		int e2shiftX = (int) ((e2.getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
		int e1shiftY = (int) ((e1.getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);
		int e2shiftY = (int) ((e2.getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);
		
		if (e1shiftX > e1.getBounds().width / 3)
			e1shiftX = e1.getBounds().width / 3;
		if (e1shiftX < -e1.getBounds().width / 3)
			e1shiftX = -e1.getBounds().width / 3;
			
		if (e2shiftX > e2.getBounds().width / 3)
			e2shiftX = e2.getBounds().width / 3;
		if (e2shiftX < -e2.getBounds().width / 3)
			e2shiftX = -e2.getBounds().width / 3;
			
		if (e1shiftY > e1.getBounds().height / 3)
			e1shiftY = e1.getBounds().height / 3;
		if (e1shiftY < -e1.getBounds().height / 3)
			e1shiftY = -e1.getBounds().height / 3;
			
		if (e2shiftY > e2.getBounds().height / 3)
			e2shiftY = e2.getBounds().height / 3;
		if (e2shiftY < -e2.getBounds().height / 3)
			e2shiftY = -e2.getBounds().height / 3;
			
		e1shiftX *= -1;
		e2shiftX *= -1;
		e1shiftY *= -1;
		e2shiftY *= -1;
		
		if (e1.getBounds().getCenterX() < e2.getBounds().getCenterX())
		{
			if (Math.abs(e1.getBounds().getCenterY() - getBounds().getCenterY()) < 100 && e1.getBounds().getMaxX() + 10 < getBounds().x)
			{
				g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x + (int) ((e1.getBounds().getMaxX() - getBounds().x) / 2.0f),
						(int) getBounds().getCenterY());
				g.drawLine(getBounds().x + (int) ((e1.getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getBounds().getCenterY(),
						getBounds().x + (int) ((e1.getBounds().getMaxX() - getBounds().x) / 2.0f), (int) e1.getBounds().getCenterY() + e1shiftY);
				g.drawLine(getBounds().x + (int) ((e1.getBounds().getMaxX() - getBounds().x) / 2.0f), (int) e1.getBounds().getCenterY() + e1shiftY,
						e1.getBounds().x + e1.getBounds().width, (int) e1.getBounds().getCenterY() + e1shiftY);
			}
			else if (e1.getBounds().getCenterX() > getBounds().x - 50)
			{
				g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x - 50, (int) getBounds().getCenterY());
				g.drawLine(getBounds().x - 50, (int) getBounds().getCenterY(), getBounds().x - 50,
						(int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine(getBounds().x - 50, (int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e1.getBounds().getCenterX() + e1shiftX,
						(int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine((int) e1.getBounds().getCenterX() + e1shiftX,
						(int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e1.getBounds().getCenterX() + e1shiftX,
						e1.getBounds().y + ((e1.getBounds().getCenterY() > getBounds().getCenterY()) ? e1.getBounds().height : 0));
			}
			else
			{
				g.drawLine(getBounds().x, (int) getBounds().getCenterY(), (int) e1.getBounds().getCenterX() + e1shiftX, (int) getBounds().getCenterY());
				g.drawLine((int) e1.getBounds().getCenterX() + e1shiftX, (int) getBounds().getCenterY(), (int) e1.getBounds().getCenterX() + e1shiftX,
						e1.getBounds().y + ((e1.getBounds().getCenterY() > getBounds().getCenterY()) ? e1.getBounds().height : 0));
			}
			if (Math.abs(e2.getBounds().getCenterY() - getBounds().getCenterY()) < 100 && e2.getBounds().x - 10 > getBounds().getMaxX())
			{
				g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(),
						getBounds().x + getBounds().width + (int) ((e2.getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY());
				g.drawLine(getBounds().x + getBounds().width + (int) ((e2.getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY(),
						getBounds().x + getBounds().width + (int) ((e2.getBounds().x - getBounds().getMaxX()) / 2.0f), (int) e2.getBounds().getCenterY() + e2shiftY);
				g.drawLine(getBounds().x + getBounds().width + (int) ((e2.getBounds().x - getBounds().getMaxX()) / 2.0f),
						(int) e2.getBounds().getCenterY() + e2shiftY, e2.getBounds().x, (int) e2.getBounds().getCenterY() + e2shiftY);
			}
			else if (e2.getBounds().getCenterX() < getBounds().x + getBounds().width + 50)
			{
				g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY());
				g.drawLine(getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50,
						(int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine(getBounds().x + getBounds().width + 50,
						(int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e2.getBounds().getCenterX() + e2shiftX,
						(int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine((int) e2.getBounds().getCenterX() + e2shiftX,
						(int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e2.getBounds().getCenterX() + e2shiftX,
						e2.getBounds().y + ((e2.getBounds().getCenterY() > getBounds().getCenterY()) ? e2.getBounds().height : 0));
			}
			else
			{
				g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), (int) e2.getBounds().getCenterX() + e2shiftX,
						(int) getBounds().getCenterY());
				g.drawLine((int) e2.getBounds().getCenterX() + e2shiftX, (int) getBounds().getCenterY(), (int) e2.getBounds().getCenterX() + e2shiftX,
						e2.getBounds().y + ((e2.getBounds().getCenterY() > getBounds().getCenterY()) ? e2.getBounds().height : 0));
			}
		}
		else
		{
			if (Math.abs(e2.getBounds().getCenterY() - getBounds().getCenterY()) < 100 && e2.getBounds().getMaxX() + 10 < getBounds().x)
			{
				g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x + (int) ((e2.getBounds().getMaxX() - getBounds().x) / 2.0f),
						(int) getBounds().getCenterY());
				g.drawLine(getBounds().x + (int) ((e2.getBounds().getMaxX() - getBounds().x) / 2.0f), (int) getBounds().getCenterY(),
						getBounds().x + (int) ((e2.getBounds().getMaxX() - getBounds().x) / 2.0f), (int) e2.getBounds().getCenterY() + e2shiftY);
				g.drawLine(getBounds().x + (int) ((e2.getBounds().getMaxX() - getBounds().x) / 2.0f), (int) e2.getBounds().getCenterY() + e2shiftY,
						e2.getBounds().x + e2.getBounds().width, (int) e2.getBounds().getCenterY() + e2shiftY);
			}
			else if (e2.getBounds().getCenterX() > getBounds().x - 50)
			{
				g.drawLine(getBounds().x, (int) getBounds().getCenterY(), getBounds().x - 50, (int) getBounds().getCenterY());
				g.drawLine(getBounds().x - 50, (int) getBounds().getCenterY(), getBounds().x - 50,
						(int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine(getBounds().x - 50, (int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e2.getBounds().getCenterX() + e2shiftX,
						(int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine((int) e2.getBounds().getCenterX() + e2shiftX,
						(int) (getBounds().getCenterY() + (e2.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e2.getBounds().getCenterX() + e2shiftX,
						e2.getBounds().y + ((e2.getBounds().getCenterY() > getBounds().getCenterY()) ? e2.getBounds().height : 0));
			}
			else
			{
				g.drawLine(getBounds().x, (int) getBounds().getCenterY(), (int) e2.getBounds().getCenterX() + e2shiftX, (int) getBounds().getCenterY());
				g.drawLine((int) e2.getBounds().getCenterX() + e2shiftX, (int) getBounds().getCenterY(), (int) e2.getBounds().getCenterX() + e2shiftX,
						e2.getBounds().y + ((e2.getBounds().getCenterY() > getBounds().getCenterY()) ? e2.getBounds().height : 0));
			}
			if (Math.abs(e1.getBounds().getCenterY() - getBounds().getCenterY()) < 100 && e1.getBounds().x - 10 > getBounds().getMaxX())
			{
				g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(),
						getBounds().x + getBounds().width + (int) ((e1.getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY());
				g.drawLine(getBounds().x + getBounds().width + (int) ((e1.getBounds().x - getBounds().getMaxX()) / 2.0f), (int) getBounds().getCenterY(),
						getBounds().x + getBounds().width + (int) ((e1.getBounds().x - getBounds().getMaxX()) / 2.0f), (int) e1.getBounds().getCenterY() + e1shiftY);
				g.drawLine(getBounds().x + getBounds().width + (int) ((e1.getBounds().x - getBounds().getMaxX()) / 2.0f),
						(int) e1.getBounds().getCenterY() + e1shiftY, e1.getBounds().x, (int) e1.getBounds().getCenterY() + e1shiftY);
			}
			else if (e1.getBounds().getCenterX() < getBounds().x + getBounds().width + 50)
			{
				g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY());
				g.drawLine(getBounds().x + getBounds().width + 50, (int) getBounds().getCenterY(), getBounds().x + getBounds().width + 50,
						(int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine(getBounds().x + getBounds().width + 50,
						(int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e1.getBounds().getCenterX() + e1shiftX,
						(int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine((int) e1.getBounds().getCenterX() + e1shiftX,
						(int) (getBounds().getCenterY() + (e1.getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) e1.getBounds().getCenterX() + e1shiftX,
						e1.getBounds().y + ((e1.getBounds().getCenterY() > getBounds().getCenterY()) ? e1.getBounds().height : 0));
			}
			else
			{
				g.drawLine(getBounds().x + getBounds().width, (int) getBounds().getCenterY(), (int) e1.getBounds().getCenterX() + e1shiftX,
						(int) getBounds().getCenterY());
				g.drawLine((int) e1.getBounds().getCenterX() + e1shiftX, (int) getBounds().getCenterY(), (int) e1.getBounds().getCenterX() + e1shiftX,
						e1.getBounds().y + ((e1.getBounds().getCenterY() > getBounds().getCenterY()) ? e1.getBounds().height : 0));
			}
		}
		
		if (e1.getBounds().getCenterX() < e2.getBounds().getCenterX())
		{
			g.drawString((e1toMany) ? "n" : "1", getBounds().x - 5, getBounds().y + 30);
			if (e1toMany)
				g.drawString((e2toMany) ? "m" : "1", getBounds().x + getBounds().width + 5, getBounds().y + 30);
			else
				g.drawString((e2toMany) ? "n" : "1", getBounds().x + getBounds().width + 5, getBounds().y + 30);
		}
		else
		{
			g.drawString((e1toMany) ? "n" : "1", getBounds().x + getBounds().width + 5, getBounds().y + 30);
			if (e1toMany)
				g.drawString((e2toMany) ? "m" : "1", getBounds().x - 5, getBounds().y + 30);
			else
				g.drawString((e2toMany) ? "n" : "1", getBounds().x - 5, getBounds().y + 30);
		}
		
		Polygon p = new Polygon();
		p.addPoint(getBounds().x, getBounds().y + getBounds().height / 2);
		p.addPoint(getBounds().x + getBounds().width / 2, getBounds().y);
		p.addPoint(getBounds().x + getBounds().width, getBounds().y + getBounds().height / 2);
		p.addPoint(getBounds().x + getBounds().width / 2, getBounds().y + getBounds().height);
		
		g.setColor(Color.WHITE);
		g.fill(p);
		
		if (isSelected())
			g.setColor(new Color(0, 150, 180));
		else
			g.setColor(Color.BLACK);
			
		g.draw(p);
		
		if (isWeak())
		{
			Polygon ip = new Polygon();
			ip.addPoint(getBounds().x + 10, getBounds().y + getBounds().height / 2);
			ip.addPoint(getBounds().x + getBounds().width / 2, getBounds().y + 5);
			ip.addPoint(getBounds().x + getBounds().width - 10, getBounds().y + getBounds().height / 2);
			ip.addPoint(getBounds().x + getBounds().width / 2, getBounds().y + getBounds().height - 5);
			
			g.draw(ip);
		}
		
		FontRenderContext frc = g.getFontRenderContext();
		Font f = new Font("Helvetica", Font.PLAIN, 18);
		TextLayout tl = new TextLayout(getName(), f, frc);
		Rectangle2D textBounds = tl.getBounds();
		tl.draw(g, (int) (getBounds().x + getBounds().width / 2 - textBounds.getWidth() / 2), getBounds().y + getBounds().height / 2 + 7);
		
	}
	
	public void setFirstEntity(Entity e1)
	{
		this.e1 = e1;
	}
	
	public void setFirstEntityToMany(boolean toMany)
	{
		e1toMany = toMany;
	}
	
	public void setSecondEntity(Entity e2)
	{
		this.e2 = e2;
	}
	
	public void setSecondEntityToMany(boolean toMany)
	{
		e2toMany = toMany;
	}

	
	
}

package model;
/**
 * EReditor
 * Entity.java
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
import model.Attribute;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

public class Entity extends ERObject
{
	private static final long serialVersionUID = 1L;

	private ArrayList<Attribute>	attributes;
	private boolean					abstractEntity;
	private Entity					parentEntity;
	private Entity					aggregatedEntity;

	public Entity()
	{
		setAttributes(new ArrayList<Attribute>());
		getBounds().x = 500;
		getBounds().y = 400;
		getBounds().width = 200;
		getBounds().height = 80;
		setName(ER_Editor.LOCALIZATION.getString("entity_default_name"));
	}

	public Entity getAggregatedEntity()
	{
		return aggregatedEntity;
	}

	public Entity getParent()
	{
		return getParentEntity();
	}

	public boolean hasAggregatedEntity()
	{
		return getAggregatedEntity() != null;
	}

	public boolean hasParentEntity()
	{
		return getParentEntity() != null;
	}

	public boolean isAbstract()
	{
		return isAbstractEntity();
	}

	@Override
	public void paint(Graphics2D g)
	{
		getBounds().width = isWeak() ? 210 : 200;
		getBounds().height = isWeak() ? 90 : 80;

		// Parent Entity

		if (getParentEntity() != null)
		{
			int shiftX = (int) ((getParentEntity().getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
			int shiftY = (int) ((getParentEntity().getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);
			shiftX *= -1;
			shiftY *= -1;

			if (isSelected())
				g.setColor(new Color(0, 150, 180));
			else
				g.setColor(Color.BLACK);
			if (Math.abs(getBounds().getCenterX() - getParentEntity().getBounds().getCenterX()) < 130)
			{
				g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getBounds().getCenterX(),
						(int) (getBounds().getCenterY() + (getParentEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine((int) getBounds().getCenterX(),
						(int) (getBounds().getCenterY() + (getParentEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) getParentEntity().getBounds().getCenterX() + shiftX,
						(int) (getBounds().getCenterY() + (getParentEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				if (getBounds().getCenterY() < getParentEntity().getBounds().getCenterY())
				{
					g.drawLine((int) getParentEntity().getBounds().getCenterX() + shiftX,
							(int) (getBounds().getCenterY() + (getParentEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
							(int) getParentEntity().getBounds().getCenterX() + shiftX, getParentEntity().getBounds().y);
					int tcPosX = (int) getParentEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getParentEntity().getBounds().y;
					g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY - 8);
					g.drawLine(tcPosX + 5, tcPosY - 8, tcPosX - 5, tcPosY - 8);
					g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY - 8);
				}
				else
				{
					g.drawLine((int) getParentEntity().getBounds().getCenterX() + shiftX,
							(int) (getBounds().getCenterY() + (getParentEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
							(int) getParentEntity().getBounds().getCenterX() + shiftX, getParentEntity().getBounds().y + getParentEntity().getBounds().height);
					int tcPosX = (int) getParentEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getParentEntity().getBounds().y + getParentEntity().getBounds().height;
					g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY + 8);
					g.drawLine(tcPosX + 5, tcPosY + 8, tcPosX - 5, tcPosY + 8);
					g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY + 8);
				}
			}
			else if (Math.abs(getBounds().getCenterY() - getParentEntity().getBounds().getCenterY()) < 100)
			{
				g.drawLine((int) getBounds().getCenterX(), (int) (getBounds().getCenterY()),
						(int) (getBounds().getCenterX() + (getParentEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
						(int) (getBounds().getCenterY()));
				g.drawLine((int) (getBounds().getCenterX() + (getParentEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
						(int) getBounds().getCenterY(),
						(int) (getBounds().getCenterX() + (getParentEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
						(int) getParentEntity().getBounds().getCenterY() + shiftY);
				if (getBounds().getCenterX() < getParentEntity().getBounds().getCenterX())
				{
					g.drawLine((int) (getBounds().getCenterX() + (getParentEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
							(int) getParentEntity().getBounds().getCenterY() + shiftY, getParentEntity().getBounds().x,
							(int) getParentEntity().getBounds().getCenterY() + shiftY);
					int tcPosX = getParentEntity().getBounds().x;
					int tcPosY = (int) getParentEntity().getBounds().getCenterY() + shiftY;
					g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY - 5);
					g.drawLine(tcPosX - 8, tcPosY - 5, tcPosX - 8, tcPosY + 5);
					g.drawLine(tcPosX - 8, tcPosY + 5, tcPosX, tcPosY);
				}
				else
				{
					g.drawLine((int) (getBounds().getCenterX() + (getParentEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
							(int) getParentEntity().getBounds().getCenterY() + shiftY, getParentEntity().getBounds().x + getParentEntity().getBounds().width,
							(int) getParentEntity().getBounds().getCenterY() + shiftY);
					int tcPosX = getParentEntity().getBounds().x + getParentEntity().getBounds().width;
					int tcPosY = (int) getParentEntity().getBounds().getCenterY() + shiftY;
					g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY - 5);
					g.drawLine(tcPosX + 8, tcPosY - 5, tcPosX + 8, tcPosY + 5);
					g.drawLine(tcPosX + 8, tcPosY + 5, tcPosX, tcPosY);
				}
			}
			else
			{
				g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getParentEntity().getBounds().getCenterX() + shiftX,
						(int) getBounds().getCenterY());
				if (getBounds().getCenterY() < getParentEntity().getBounds().getCenterY())
				{
					g.drawLine((int) getParentEntity().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
							(int) getParentEntity().getBounds().getCenterX() + shiftX, getParentEntity().getBounds().y);
					int tcPosX = (int) getParentEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getParentEntity().getBounds().y;
					g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY - 8);
					g.drawLine(tcPosX + 5, tcPosY - 8, tcPosX - 5, tcPosY - 8);
					g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY - 8);
				}
				else
				{
					g.drawLine((int) getParentEntity().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
							(int) getParentEntity().getBounds().getCenterX() + shiftX, (int) getParentEntity().getBounds().getMaxY());
					int tcPosX = (int) getParentEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getParentEntity().getBounds().y + getParentEntity().getBounds().height;
					g.drawLine(tcPosX, tcPosY, tcPosX - 5, tcPosY + 8);
					g.drawLine(tcPosX + 5, tcPosY + 8, tcPosX - 5, tcPosY + 8);
					g.drawLine(tcPosX, tcPosY, tcPosX + 5, tcPosY + 8);
				}
			}

		}

		// Aggregated Entity

		if (getAggregatedEntity() != null)
		{
			int shiftX = (int) ((getAggregatedEntity().getBounds().getCenterX() - getBounds().getCenterX()) * 0.05f);
			int shiftY = (int) ((getAggregatedEntity().getBounds().getCenterY() - getBounds().getCenterY()) * 0.05f);
			shiftX *= -1;
			shiftY *= -1;

			if (isSelected())
				g.setColor(new Color(0, 150, 180));
			else
				g.setColor(Color.BLACK);
			if (Math.abs(getBounds().getCenterX() - getAggregatedEntity().getBounds().getCenterX()) < 130)
			{
				g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getBounds().getCenterX(),
						(int) (getBounds().getCenterY() + (getAggregatedEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				g.drawLine((int) getBounds().getCenterX(),
						(int) (getBounds().getCenterY() + (getAggregatedEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
						(int) getAggregatedEntity().getBounds().getCenterX() + shiftX,
						(int) (getBounds().getCenterY() + (getAggregatedEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f));
				if (getBounds().getCenterY() < getAggregatedEntity().getBounds().getCenterY())
				{
					g.drawLine((int) getAggregatedEntity().getBounds().getCenterX() + shiftX,
							(int) (getBounds().getCenterY() + (getAggregatedEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
							(int) getAggregatedEntity().getBounds().getCenterX() + shiftX, getAggregatedEntity().getBounds().y);
					int tcPosX = (int) getAggregatedEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getAggregatedEntity().getBounds().y;
					g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY - 12);
					g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY - 12);
					g.drawLine(tcPosX, tcPosY - 24, tcPosX - 8, tcPosY - 12);
					g.drawLine(tcPosX, tcPosY - 24, tcPosX + 8, tcPosY - 12);
				}
				else
				{
					g.drawLine((int) getAggregatedEntity().getBounds().getCenterX() + shiftX,
							(int) (getBounds().getCenterY() + (getAggregatedEntity().getBounds().getCenterY() - getBounds().getCenterY()) / 2.0f),
							(int) getAggregatedEntity().getBounds().getCenterX() + shiftX,
							getAggregatedEntity().getBounds().y + getAggregatedEntity().getBounds().height);
					int tcPosX = (int) getAggregatedEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getAggregatedEntity().getBounds().y + getAggregatedEntity().getBounds().height;
					g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY + 12);
					g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY + 12);
					g.drawLine(tcPosX, tcPosY + 24, tcPosX - 8, tcPosY + 12);
					g.drawLine(tcPosX, tcPosY + 24, tcPosX + 8, tcPosY + 12);
				}
			}
			else if (Math.abs(getBounds().getCenterY() - getAggregatedEntity().getBounds().getCenterY()) < 100)
			{
				g.drawLine((int) getBounds().getCenterX(), (int) (getBounds().getCenterY()),
						(int) (getBounds().getCenterX() + (getAggregatedEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
						(int) (getBounds().getCenterY()));
				g.drawLine((int) (getBounds().getCenterX() + (getAggregatedEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
						(int) getBounds().getCenterY(),
						(int) (getBounds().getCenterX() + (getAggregatedEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
						(int) getAggregatedEntity().getBounds().getCenterY() + shiftY);
				if (getBounds().getCenterX() < getAggregatedEntity().getBounds().getCenterX())
				{
					g.drawLine((int) (getBounds().getCenterX() + (getAggregatedEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
							(int) getAggregatedEntity().getBounds().getCenterY() + shiftY, getAggregatedEntity().getBounds().x,
							(int) getAggregatedEntity().getBounds().getCenterY() + shiftY);
					int tcPosX = getAggregatedEntity().getBounds().x;
					int tcPosY = (int) getAggregatedEntity().getBounds().getCenterY() + shiftY;
					g.drawLine(tcPosX, tcPosY, tcPosX - 12, tcPosY - 8);
					g.drawLine(tcPosX, tcPosY, tcPosX - 12, tcPosY + 8);
					g.drawLine(tcPosX - 12, tcPosY - 8, tcPosX - 24, tcPosY);
					g.drawLine(tcPosX - 12, tcPosY + 8, tcPosX - 24, tcPosY);
				}
				else
				{
					g.drawLine((int) (getBounds().getCenterX() + (getAggregatedEntity().getBounds().getCenterX() - getBounds().getCenterX()) / 2.0f),
							(int) getAggregatedEntity().getBounds().getCenterY() + shiftY, getAggregatedEntity().getBounds().x + getAggregatedEntity().getBounds().width,
							(int) getAggregatedEntity().getBounds().getCenterY() + shiftY);
					int tcPosX = getAggregatedEntity().getBounds().x + getAggregatedEntity().getBounds().width;
					int tcPosY = (int) getAggregatedEntity().getBounds().getCenterY() + shiftY;
					g.drawLine(tcPosX + 24, tcPosY, tcPosX + 12, tcPosY - 8);
					g.drawLine(tcPosX + 24, tcPosY, tcPosX + 12, tcPosY + 8);
					g.drawLine(tcPosX + 12, tcPosY + 8, tcPosX, tcPosY);
					g.drawLine(tcPosX + 12, tcPosY - 8, tcPosX, tcPosY);
				}
			}
			else
			{
				g.drawLine((int) getBounds().getCenterX(), (int) getBounds().getCenterY(), (int) getAggregatedEntity().getBounds().getCenterX() + shiftX,
						(int) getBounds().getCenterY());
				if (getBounds().getCenterY() < getAggregatedEntity().getBounds().getCenterY())
				{
					g.drawLine((int) getAggregatedEntity().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
							(int) getAggregatedEntity().getBounds().getCenterX() + shiftX, getAggregatedEntity().getBounds().y);
					int tcPosX = (int) getAggregatedEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getAggregatedEntity().getBounds().y;
					g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY - 12);
					g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY - 12);
					g.drawLine(tcPosX, tcPosY - 24, tcPosX - 8, tcPosY - 12);
					g.drawLine(tcPosX, tcPosY - 24, tcPosX + 8, tcPosY - 12);
				}
				else
				{
					g.drawLine((int) getAggregatedEntity().getBounds().getCenterX() + shiftX, (int) getBounds().getCenterY(),
							(int) getAggregatedEntity().getBounds().getCenterX() + shiftX, (int) getAggregatedEntity().getBounds().getMaxY());
					int tcPosX = (int) getAggregatedEntity().getBounds().getCenterX() + shiftX;
					int tcPosY = getAggregatedEntity().getBounds().y + getAggregatedEntity().getBounds().height;
					g.drawLine(tcPosX, tcPosY, tcPosX - 8, tcPosY + 12);
					g.drawLine(tcPosX, tcPosY, tcPosX + 8, tcPosY + 12);
					g.drawLine(tcPosX, tcPosY + 24, tcPosX - 8, tcPosY + 12);
					g.drawLine(tcPosX, tcPosY + 24, tcPosX + 8, tcPosY + 12);
				}
			}

		}

		// Entity

		float a_radius_x = Math.max(0, getAttributes().size() - 8) * 15 + 200;
		float a_radius_y = Math.max(0, getAttributes().size() - 8) * 15 + 150;

		for (int i = 0; i < getAttributes().size(); i++)
		{
			float angle = (float) i / getAttributes().size() * -2.0f * 3.141592653f + 3.141592653f;
			float posX = (float) (Math.cos(angle) * a_radius_x + getBounds().getCenterX());
			float posY = (float) (Math.sin(angle) * -a_radius_y + getBounds().getCenterY());

			if (isSelected())
				g.setColor(new Color(0, 150, 180));
			else
				g.setColor(Color.BLACK);
			g.drawLine((int) posX, (int) posY, getBounds().x + getBounds().width / 2, getBounds().y + getBounds().height / 2);

			if (getAttributes().get(i).getName().length() != 0)
			{
				FontRenderContext frc = g.getFontRenderContext();
				Font f = new Font("Helvetica", Font.PLAIN, 12);
				TextLayout tl = new TextLayout(getAttributes().get(i).getName(), f, frc);
				Rectangle2D textBounds = tl.getBounds();

				int radius = Math.max((int) (textBounds.getWidth() / 2) + 8, 40);

				g.setColor(Color.WHITE);
				g.fillOval((int) posX - radius, (int) posY - 40, 2 * radius, 80);

				if (isSelected())
					g.setColor(new Color(0, 150, 180));
				else
					g.setColor(Color.BLACK);
				g.drawOval((int) posX - radius, (int) posY - 40, 2 * radius, 80);
				if (getAttributes().get(i).isKeyAttribute())
					g.fillArc((int) posX - radius, (int) posY - 40, 2 * radius, 80, 180, 180);

				tl.draw(g, (int) (posX - textBounds.getWidth() / 2), posY + (getAttributes().get(i).isKeyAttribute() ? -2 : 3));
			}

		}

		g.setColor(Color.WHITE);
		g.fill(getBounds());

		if (isSelected())
			g.setColor(new Color(0, 150, 180));
		else
			g.setColor(Color.BLACK);

		g.draw(getBounds());
		if (isWeak())
		{
			Rectangle innerRect = new Rectangle(getBounds().x + 5, getBounds().y + 5, getBounds().width - 10, getBounds().height - 10);
			g.draw(innerRect);
		}
		if (getName().length() != 0)
		{
			FontRenderContext frc = g.getFontRenderContext();
			Font f = new Font("Helvetica", Font.PLAIN, 18);
			TextLayout tl = new TextLayout(getName(), f, frc);
			Rectangle2D textBounds = tl.getBounds();
			tl.draw(g, (int) (getBounds().x + getBounds().width / 2 - textBounds.getWidth() / 2), getBounds().y + getBounds().height / 2 + 7);
		}
	}

	public void setAbstract(boolean aFlag)
	{
		setAbstractEntity(aFlag);
	}

	public void setAggregatedEntity(Entity aggregatedEntity)
	{
		this.aggregatedEntity = aggregatedEntity;
	}

	public void setParentEntity(Entity e)
	{
		parentEntity = e;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}

	public boolean isAbstractEntity() {
		return abstractEntity;
	}

	public void setAbstractEntity(boolean abstractEntity) {
		this.abstractEntity = abstractEntity;
	}

	public Entity getParentEntity() {
		return parentEntity;
	}

	public boolean addAttribute(Attribute a){
		return this.getAttributes().add(a);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Entity entity = (Entity) o;
		return getName().equals(entity.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
}
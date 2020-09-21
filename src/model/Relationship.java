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
import view.er_objects.RelationshipView;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class Relationship extends ERObject<RelationshipView>
{

	private static final long serialVersionUID = 1L;
	
	private Entity	e1;
	private Entity	e2;
	private boolean	e1toMany;
	private boolean	e2toMany;
	
	public Relationship()
	{

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

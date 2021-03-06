package view;
/**
  * EReditor
  * view.ER_FrameLayout.java
  * Created by Palle on 14.05.2014
  * Copyright (c) 2014 - 2017 Palle.
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

import controller.ERSelectionNotifier;
import model.DescriptionBox;
import model.Entity;
import model.Relationship;

import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class ER_FrameLayout implements LayoutManager, ERSelectionNotifier
{
	private boolean entityVisible;
	
	public ER_FrameLayout()
	{
		entityVisible = false;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp)
	{
	}
	
	@Override
	public void didSelectEntity(Entity e)
	{
		entityVisible = e != null;
	}
	
	@Override
	public void didSelectRelationship(Relationship r)
	{
		entityVisible = r != null;
	}

	@Override
	public void didSelectDescriptionBox(DescriptionBox b)
	{
		entityVisible = b != null;
	}
	
	@Override
	public void layoutContainer(Container parent)
	{
		for (Component c : parent.getComponents())
		{
			if (entityVisible)
			{
				if (c instanceof JScrollPane)
				{
					c.setBounds(0, 0, parent.getWidth() - 300, parent.getHeight());
				}
				else if (c instanceof EntityEditor)
				{
					c.setBounds(parent.getWidth() - 300, 0, 300, parent.getHeight());
				}
			}
			else
			{
				if (c instanceof JScrollPane)
				{
					c.setBounds(0, 0, parent.getWidth(), parent.getHeight());
				}
				else if (c instanceof EntityEditor)
				{
					c.setBounds(parent.getWidth(), 0, 0, parent.getHeight());
				}
			}
		}
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		return new Dimension(350, 200);
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		return new Dimension(1280, 720);
	}
	
	@Override
	public void removeLayoutComponent(Component comp)
	{
	
	}
	
}

package model;
/**
 * EReditor
 * DescriptionBox.java
 * Created by Palle on 30.05.2014
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

import main.ER_Editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class DescriptionBox extends ERObject
{
	private static final long serialVersionUID = 1L;

	private String descriptionText;

	public DescriptionBox()
	{
		descriptionText = ER_Editor.LOCALIZATION.getString("description_default_text");
		setName( ER_Editor.LOCALIZATION.getString("description_default_title"));
	}

	public DescriptionBox(String title, String text)
	{
		descriptionText = text;
		setName(title);
	}

	public String getText()
	{
		return descriptionText;
	}

	@Override
	public void paint(Graphics2D g)
	{
		g.setColor(Color.WHITE);

		if (descriptionText.length() != 0)
		{
			String[] lines = descriptionText.split("\n");
			int maxWidth = 0;
			for (String line : lines)
			{
				if (line.length() == 0)
					continue;
				FontRenderContext frc = g.getFontRenderContext();
				Font f = new Font("Helvetica", Font.PLAIN, 14);
				TextLayout tl = new TextLayout(line, f, frc);
				maxWidth = Math.max((int) tl.getBounds().getWidth(), maxWidth);
			}

			FontRenderContext frc = g.getFontRenderContext();
			Font f = new Font("Helvetica", Font.BOLD, 22);
			TextLayout tl = new TextLayout(getName(), f, frc);
			maxWidth = Math.max((int) tl.getBounds().getWidth(), maxWidth);

			getBounds().width = maxWidth + 20;

			int height = getName().trim().isEmpty() ? 0 : 24;
			height += lines.length * 18;

			getBounds().height = height + 20;
		}

		g.fillRoundRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height, 10, 10);

		if (isSelected())
			g.setColor(new Color(0, 150, 180));
		else
			g.setColor(Color.BLACK);

		g.drawRoundRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height, 10, 10);

		if (!getName().trim().isEmpty())
		{
			FontRenderContext frc = g.getFontRenderContext();
			Font f = new Font("Helvetica", Font.BOLD, 22);
			TextLayout tl = new TextLayout(getName(), f, frc);
			tl.draw(g, getBounds().x + 10, (float) (getBounds().y + 17 + 11));
		}

		if (descriptionText.length() != 0)
		{
			String[] lines = descriptionText.split("\n");

			int offset;

			if (getName().trim().isEmpty())
			{
				offset = 20;
			}
			else
			{
				offset = 48;
			}

			for (int i = 0; i < lines.length; i++)
			{
				String line = lines[i];
				if (line.length() == 0)
					continue;
				FontRenderContext frc = g.getFontRenderContext();
				Font f = new Font("Helvetica", Font.PLAIN, 14);
				TextLayout tl = new TextLayout(line, f, frc);
				tl.draw(g, getBounds().x + 10, getBounds().y + offset + i * 18);
			}
		}

	}

	public void setText(String text)
	{
		descriptionText = text;
	}

}
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
import view.er_objects.DescriptionView;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class DescriptionBox extends ERObject<DescriptionView>
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


	public void setText(String text)
	{
		descriptionText = text;
	}

}
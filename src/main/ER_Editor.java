package main;
/**
  * EReditor
  * main.ER_Editor.java
  * Created by Palle on 14.05.2014
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

import view.ERFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ER_Editor
{
	public static ResourceBundle LOCALIZATION;
	
	private static List<ERFrame> openedFrames;

	public static void main(String[] args) throws MalformedURLException {
		try
		{
			System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		
		}
		Locale locale = Locale.getDefault();

		setLOCALIZATION(ResourceBundle.getBundle("Localizable", locale));
		setOpenedFrames(new ArrayList<>());
		SwingUtilities.invokeLater(ERFrame::new);
	}

	public static ResourceBundle getLOCALIZATION() {
		return LOCALIZATION;
	}

	public static void setLOCALIZATION(ResourceBundle LOCALIZATION) {
		ER_Editor.LOCALIZATION = LOCALIZATION;
	}

	public static List<ERFrame> getOpenedFrames() {
		return openedFrames;
	}

	public static void setOpenedFrames(List<ERFrame> openedFrames) {
		ER_Editor.openedFrames = openedFrames;
	}
}

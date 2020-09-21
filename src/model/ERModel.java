package model;
/**
  * EReditor
  * model.ERModel.java
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

import main.ER_Editor;
import persistence.*;
import persistence.exporter.Exporter;
import persistence.exporter.HTMLExporter;
import persistence.filter.CSVFilter;
import persistence.filter.ExportableFileFormat;
import persistence.filter.HTMLFilter;
import view.er_objects.ERObjectView;
import view.er_objects.EntityView;


import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ERModel implements Saveable {


	private List<Entity> entities;
	private List<Relationship> relationships;
	private List<DescriptionBox> descriptions;

	private File fileName;

	private boolean saved;

	public static ERModel open(File f) throws IOException, ClassNotFoundException {
		if(f.getName().endsWith(Filetypes.SERIALIZED.getExtension())){
			return new OpenSerialized().open(f);
		}else if(f.getName().endsWith(Filetypes.MARKDOWN.getExtension())){
			return new OpenMarkdowned().open(f);
		}
		throw new IOException("Unsupported filetype!");
	}

	public static ERModel open(JFrame parentFrame) {
		FileDialog dialog = new FileDialog(parentFrame, "ER-Modelldatei ausw\u00e4hlen...", FileDialog.LOAD);
		dialog.setFile("*.erm");
		dialog.setFile("*.erm");

		dialog.setFilenameFilter((dir, name) -> name.endsWith(".erm"));
		dialog.setVisible(true);
		String filename = dialog.getFile();
		String dir = dialog.getDirectory();
		if (filename != null) {
			try {
				return open(new File(dir + filename));
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(null,
						ER_Editor.getLOCALIZATION().getString("open_error_message") + "\n" + e.getLocalizedMessage(),
						ER_Editor.getLOCALIZATION().getString("open_error_title"), JOptionPane.ERROR_MESSAGE);
			}
		}
		return null;
	}


	public ERModel() {
		setEntities(new ArrayList<>());
		setRelationships(new ArrayList<>());
		setDescriptions(new ArrayList<>());
	}

	public void addEntity() {
		for (Entity e1 : getEntities())
			e1.getView().deselect();
		getEntities().add(new EntityView().getErObject());
	}

	public void export() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			try {
				ImageIO.write(render(), "png", f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, ER_Editor.getLOCALIZATION().getString("image_export_error_message"),
						ER_Editor.getLOCALIZATION().getString("image_export_error_title"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void exportModel() {
		ERModelConverter converter = new ERModelConverter(this);
		converter.convert();
		converter.display();
	}

	public String getFilename() {
		if (isSaved())
			return getFileName().getName();
		else
			return ER_Editor.getLOCALIZATION().getString("model_name");
	}

	public boolean isEmpty() {
		return getEntities().isEmpty() && getRelationships().isEmpty() && getDescriptions().isEmpty() && !isSaved();
	}

	public void layoutBoxes() {

	}

	public boolean needsSave() {
		if (!isSaved())
			return true;
		else {
			try {
				return !open(getFileName()).equals(this);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public BufferedImage render() {
		int maxX = 0;
		int maxY = 0;

		for (Relationship r : getRelationships()) {
			Rectangle bounds = r.getView().getBounds();
			maxX = (int) Math.max(maxX, bounds.getMaxX() + 200);
			maxY = (int) Math.max(maxY, bounds.getMaxY() + 200);
		}

		for (Entity e : getEntities()) {
			Rectangle bounds = e.getView().getBounds();
			maxX = (int) Math.max(maxX, bounds.getMaxX() + 200);
			maxY = (int) Math.max(maxY, bounds.getMaxY() + 200);
		}

		BufferedImage image = new BufferedImage(maxX * 2, maxY * 2, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, maxX * 2, maxY * 2);
		g.scale(2.0f, 2.0f);

		for (ERObjectView<Relationship> r : getRelationshipViews()) {
			r.deselect();
			r.paint(g);
		}
		for (ERObjectView<Entity> e : getEntityViews()) {
			e.deselect();
			e.paint(g);
		}

		g.dispose();
		return image;
	}

	public boolean save(JFrame parentFrame) {
		boolean success = false;
		if (!isSaved())
			success = saveAs(true, parentFrame);
		else
			success = save(getFileName());
		setSaved(isSaved() || success);
		return success;
	}

	public boolean saveAs(JFrame parentFrame) {
		return saveAs(true, parentFrame);
	}

	public boolean saveAsCopy(JFrame parentFrame) {
		return saveAs(false, parentFrame);
	}

	public boolean save(File f) {
		Saveable saveable;
		if (f.getAbsolutePath().endsWith(Filetypes.SERIALIZED.getExtension())) {
			saveable = new ERMsaver(getEntities(), getRelationships(), getDescriptions());

		} else {// if (f.getAbsolutePath().endsWith(persistence.Filetypes.MARKDOWN.getExtension())) {
			saveable = new MarkdownSaver(getEntities(), getRelationships(), getDescriptions());
		}
		return saveable.save(new File(f.getAbsolutePath()));
	}

	private boolean saveAs(boolean keepNewFileName, JFrame parent) {
		JFileChooser chooser = new JFileChooser();
		// chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ERM & ERMD models",
				Filetypes.MARKDOWN.getExtensionWithoutDot(), Filetypes.SERIALIZED.getExtensionWithoutDot());
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();

			if (selectedFile != null) {
				String dir = selectedFile.getPath();
				if (keepNewFileName)
					setFileName(new File(dir));
				return save(new File(dir));
			}

		}
		return false;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}

	public void setDescriptions(List<DescriptionBox> descriptions) {
		this.descriptions = descriptions;
	}

	public void setFileName(File f) {
		this.fileName = f;
	}

	public void setSaved(boolean b) {
		this.saved = b;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Relationship> getRelationships() {
		return relationships;
	}

	public List<DescriptionBox> getDescriptions() {
		return descriptions;
	}

	public File getFileName() {
		return fileName;
	}

	public boolean isSaved() {
		return saved;
	}

    public void exportDescriptions() {
		//System.out.println(body().withText("TEST").renderFormatted());
		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.addChoosableFileFilter(new HTMLFilter());
		chooser.addChoosableFileFilter(new CSVFilter());


		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			try {

				((ExportableFileFormat<DescriptionBox>) chooser.getFileFilter())
						.export(this.descriptions, f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, ER_Editor.getLOCALIZATION().getString("description_export_error_message"),
						ER_Editor.getLOCALIZATION().getString("description_export_error_title"), JOptionPane.ERROR_MESSAGE);
			}
		}

    }

	public List<ERObjectView<Entity>> getEntityViews() {
		return entities.stream().map(entity -> entity.getView()).collect(Collectors.toCollection(ArrayList::new));
	}

	public List<ERObjectView<Relationship>> getRelationshipViews() {
		return relationships.stream().map(Relationship::getView).collect(Collectors.toCollection(ArrayList::new));
	}

	public List<ERObjectView<DescriptionBox>> getDescriptionViews() {
		return descriptions.stream().map(DescriptionBox::getView).collect(Collectors.toCollection(ArrayList::new));
	}

	public List<ERObjectView> getAllERObjectViews() {
		List erViewList = this.getEntityViews();
		erViewList.addAll(this.getRelationshipViews());
		erViewList.addAll(this.getDescriptionViews());
		return erViewList;
	}
}

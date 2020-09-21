package persistence.filter;

import model.DescriptionBox;
import persistence.exporter.HTMLExporter;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HTMLFilter extends FileFilter implements ExportableFileFormat<DescriptionBox> {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        } else {
            String filename = f.getName().toLowerCase();
            return filename.endsWith(".html") || filename.endsWith(".htm");
        }
    }


    @Override
    public String getDescription() {
        return "HTML Website (*.html)";
    }

    @Override
    public void export(List<DescriptionBox> objects, File f) throws IOException {
        new HTMLExporter(objects).exportToFile(f);
    }
}

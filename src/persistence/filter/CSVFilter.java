package persistence.filter;

import model.DescriptionBox;
import persistence.exporter.CSVExporter;
import persistence.exporter.Exporter;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CSVFilter extends FileFilter implements ExportableFileFormat<DescriptionBox>{
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        } else {
            String filename = f.getName().toLowerCase();
            return filename.endsWith(".csv");
        }
    }

    @Override
    public String getDescription() {
        return "Comma separated values (*.csv)";
    }

    @Override
    public void export(List<DescriptionBox> objects, File f) throws IOException {
        new CSVExporter(objects).exportToFile(f);
    }
}

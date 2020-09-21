package persistence.filter;

import model.ERObject;
import persistence.exporter.Exporter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ExportableFileFormat<U extends ERObject> {
    void export(List<U> objects, File f) throws IOException;
}

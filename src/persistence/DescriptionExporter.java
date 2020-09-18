package persistence;

import model.DescriptionBox;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public interface DescriptionExporter<T> {

    void setDescriptions(List<DescriptionBox> descriptions);

    public void exportToFile(File f) throws IOException;

    public void exportToStream(PrintStream stream);

    void exportToDisplay();

    T generate();
}

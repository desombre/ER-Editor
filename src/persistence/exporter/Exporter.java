package persistence.exporter;

import model.DescriptionBox;
import model.ERObject;

import java.io.*;
import java.util.List;

public interface Exporter<U extends ERObject> {

    void setData(List<U> descriptions);


    public default void exportToFile(File f) throws IOException {
        FileWriter fileWriter = new FileWriter(f.getAbsolutePath() + determineFileExtension(f));
        fileWriter.write(generate());
        fileWriter.close();

    }

    public default void exportToStream(PrintStream stream) {
        stream.println(generate());
        stream.close();
    }

    public default void exportToDisplay() {
        exportToStream(System.out);
    }

    String generate();

    String determineFileExtension(File f);
}

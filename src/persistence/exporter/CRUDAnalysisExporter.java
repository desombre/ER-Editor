package persistence.exporter;

import model.Entity;

import java.io.File;
import java.util.List;

public class CRUDAnalysisExporter implements Exporter<Entity> {

    private List<Entity> entities;

    public CRUDAnalysisExporter(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public void setData(List<Entity> data) {
        this.entities = data;
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append(" ;CREATE;READ;UPDATE;DELETE");
        builder.append(System.lineSeparator());
        for (Entity e : entities) {
            builder.append(e.getName()).append(";;;;");
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    @Override
    public String determineFileExtension(File f) {
        return f.getAbsolutePath().endsWith(".csv") ? "" : ".csv";
    }
}

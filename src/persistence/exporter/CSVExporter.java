package persistence.exporter;

import main.ER_Editor;
import model.DescriptionBox;

import java.io.File;
import java.util.List;

public class CSVExporter implements Exporter<DescriptionBox> {

    private List<DescriptionBox> descriptions;

    public CSVExporter(List<DescriptionBox> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public void setData(List<DescriptionBox> data) {
        this.descriptions = data;
    }

    @Override
    public String generate() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name;"+ ER_Editor.getLOCALIZATION().getString("description_box_description"));
        builder.append(System.getProperty("line.separator"));
        for (DescriptionBox description : descriptions) {
            builder.append(description.getName());
            builder.append(";");
            builder.append(description.getText());

            builder.append(System.getProperty("line.separator"));

        }
        return builder.toString();
    }


    @Override
    public String determineFileExtension(File f) {
        return f.getAbsolutePath().endsWith(".csv") ? "" : ".csv";
    }
}

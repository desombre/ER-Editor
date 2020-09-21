package persistence.exporter;

import model.DescriptionBox;
import persistence.exporter.Exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static j2html.TagCreator.*;
import static main.ER_Editor.LOCALIZATION;

public class HTMLExporter implements Exporter<DescriptionBox> {

    private List<DescriptionBox> descriptions;

    public HTMLExporter(List<DescriptionBox> descriptions) {
        this.descriptions = descriptions;
    }

    public String generate() {
        return html(
                head(
                        title(LOCALIZATION.getString("description_default_text")),
                        link().withRel("stylesheet").withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css")

                ),
                body(
                        table(thead(
                                tr(
                                        th(LOCALIZATION.getString("entity_name")),
                                        th(LOCALIZATION.getString("description_default_text") + ":")
                                )
                                ),
                                tbody(
                                        each(descriptions, description -> tr(
                                                td(description.getName()),
                                                td(description.getText())
                                        ))
                                )
                        ).withClass("table")
                )
        ).render();
    }


    @Override
    public void setData(List<DescriptionBox> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String determineFileExtension(File f) {
        return  f.getAbsolutePath().endsWith(".html") ||  f.getAbsolutePath().endsWith(".htm") ? "" : ".html";

    }
}

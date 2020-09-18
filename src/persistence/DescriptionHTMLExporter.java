package persistence;

import model.DescriptionBox;


import java.io.*;
import java.util.List;

import static j2html.TagCreator.*;
import static main.ER_Editor.LOCALIZATION;

public class DescriptionHTMLExporter implements DescriptionExporter<String> {

    private List<DescriptionBox> descriptions;

    public DescriptionHTMLExporter(List<DescriptionBox> descriptions) {
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
    public void exportToFile(File f) throws IOException {
        String fileExtension = f.getAbsolutePath().endsWith(".html") ? "" : ".html";
        FileWriter fileWriter = new FileWriter(f.getAbsolutePath()+fileExtension);
        fileWriter.write(generate());
        fileWriter.close();

    }

    @Override
    public void exportToStream(PrintStream stream) {
        stream.println(generate());
        stream.close();
    }

    @Override
    public void exportToDisplay() {
        exportToStream(System.out);
    }

    @Override
    public void setDescriptions(List<DescriptionBox> descriptions) {
        this.descriptions = descriptions;
    }


}

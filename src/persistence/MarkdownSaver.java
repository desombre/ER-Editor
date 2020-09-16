package persistence;

import model.Attribute;
import model.DescriptionBox;
import model.Entity;
import model.Relationship;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class MarkdownSaver implements Saveable {
    protected List<Entity> entities;
    protected List<Relationship> relationships;
    protected List<DescriptionBox> descriptions;

    public MarkdownSaver(List<Entity> entities, List<Relationship> relationships, List<DescriptionBox> descriptions) {
        this.descriptions = descriptions;
        this.entities = entities;
        this.relationships = relationships;
    }

    @Override
    public boolean save(File f) {
        boolean success = true;

        try {
            FileOutputStream fos = new FileOutputStream(f);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (Entity e : entities) {
                // Save model.Entity(-Names)
                bw.write("e, " + e.getName());
                bw.newLine();
                // Save Weaknesses
                if (e.isWeak()) {
                    bw.write("w, " + e.getName());
                    bw.newLine();

                }

                // Save generalization
                if (e.hasParentEntity()) {
                    bw.write("^, " + e.getParent().getName() + ", " + e.getName());
                    bw.newLine();

                }

                // save Aggregation
                if (e.getAggregatedEntity() != null) {
                    bw.write("a, " + e.getAggregatedEntity().getName() + ", " + e.getName());
                    bw.newLine();

                }
                // Save Attributes
                for (Attribute a : e.getAttributes()) {
                    String keyAttribute = a.isKeyAttribute() ? ", _" : "";
                    bw.write("@, " + e.getName() + ", " + a.getName() + keyAttribute);
                    bw.newLine();
                }

            }

            // Save Relationships
            for (Relationship r : relationships) {
                String cardinalityToOne = r.getSecondEntityToMany() ? "n" : "1";
                String cardinalityToTwo = r.getFirstEntityToMany() ? "n" : "1";

                String stringBuilder = r.getFirstEntity().getName() + ", " +
                        cardinalityToTwo + ", " +
                        r.getName() + ", " +
                        cardinalityToOne + ", " +
                        r.getSecondEntity().getName() + ", ";
                bw.write("r, " + stringBuilder);
                bw.newLine();
            }

            // Save Descriptions
            for (DescriptionBox d : descriptions) {
                bw.write("d, " + d.getName() + ", " + d.getText());
                bw.newLine();
            }

            bw.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            success = false;
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            success = false;
            e1.printStackTrace();
        }
        return success;
    }

}

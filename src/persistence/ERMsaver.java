package persistence;

import main.ER_Editor;
import model.DescriptionBox;
import model.Entity;
import model.Relationship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JOptionPane;

public class ERMsaver implements Saveable {

    protected List<Entity> entities;
    protected List<Relationship> relationships;
    protected List<DescriptionBox> descriptions;

    public ERMsaver(List<Entity> entities, List<Relationship> relationships, List<DescriptionBox> descriptions) {
        this.descriptions = descriptions;
        this.entities = entities;
        this.relationships = relationships;
    }   

    @Override
    public boolean save(final File f) {
        try {
            final FileOutputStream fos = new FileOutputStream(f);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(entities);
            oos.writeObject(relationships);
            oos.writeObject(descriptions);
            oos.close();
            // XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new
            // FileOutputStream(f)));
            // encoder.writeObject(entities);
            // encoder.writeObject(relationships);
            // encoder.close();
            return true;
        } catch (final FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    ER_Editor.getLOCALIZATION().getString("save_error_message") + "\n" + e.getLocalizedMessage(),
                    ER_Editor.getLOCALIZATION().getString("save_error_title"), JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (final IOException e) {
            JOptionPane.showMessageDialog(null,
                    ER_Editor.getLOCALIZATION().getString("save_error_message") + "\n" + e.getLocalizedMessage(),
                    ER_Editor.getLOCALIZATION().getString("save_error_title"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}

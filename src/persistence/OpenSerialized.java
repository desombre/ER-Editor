package persistence;

import model.DescriptionBox;
import model.ERModel;
import model.Entity;
import model.Relationship;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class OpenSerialized implements Openable {
    
    @SuppressWarnings("unchecked")
    @Override
    public ERModel open(File f) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ERModel model = new ERModel();
        model.setEntities((List<Entity>) ois.readObject());
        model.setRelationships((List<Relationship>) ois.readObject());
        model.setDescriptions((List<DescriptionBox>) ois.readObject());
        model.setFileName (f);
        model.setSaved(true);
        ois.close();
        return model;

    }

}

package persistence;

import model.ERModel;

import java.io.File;
import java.io.IOException;

public interface Openable {
    public ERModel open(File f) throws IOException, ClassNotFoundException;
}

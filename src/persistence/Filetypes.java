package persistence;

import java.io.File;
import java.util.Arrays;

public enum Filetypes {
    SERIALIZED("erm"), MARKDOWN("ermd");

    private final String fileExtension;

    private Filetypes(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getExtension() {
        return "." + this.fileExtension;
    }

    public String getExtensionWithoutDot() {
        return this.fileExtension;
    }

    public static boolean isValidExtension(String filename) {
        return Arrays.asList(Filetypes.values()).stream().anyMatch(type -> filename.endsWith(type.getExtension()));
    }

    public static boolean isValidExtension(File file) {
        return Filetypes.isValidExtension(file.getName());
    }

}

package com.luke.fcmanagement.utils.file_utils;

import com.luke.fcmanagement.constants.SaveFileType;
import com.luke.fcmanagement.utils.file_utils.FileSaver;
import com.luke.fcmanagement.utils.file_utils.impl.LocalSaveFile;

import java.util.Objects;

public class FileSaverFactory {
    public static final FileSaver getFileSaver(String typeString) {
        SaveFileType type = SaveFileType.getType(typeString);
        FileSaver fileSaver = null;
        switch (type) {
            case LOCAL:
                fileSaver = new LocalSaveFile();
                break;
            default:
                break;
        }
        if (Objects.isNull(fileSaver))
            throw new IllegalArgumentException("Invalid file saver type: " + type);
        return fileSaver;
    }
}

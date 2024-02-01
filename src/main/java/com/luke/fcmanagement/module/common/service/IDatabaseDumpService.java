package com.luke.fcmanagement.module.common.service;

import java.io.IOException;

public interface IDatabaseDumpService {
    void dumpDatabaseToFileTarget(String outputPath);

    void deleteAllFilesOfDumpFolderTarget(String path);

    void dumpDatabaseToFile(String outputPath);

    void deleteAllFilesOfDumpFolder(String path);
}

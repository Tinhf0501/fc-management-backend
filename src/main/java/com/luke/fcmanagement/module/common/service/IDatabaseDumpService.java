package com.luke.fcmanagement.module.common.service;

public interface IDatabaseDumpService {
    void dumpDatabaseToFileTarget(String outputPath);

    void deleteAllFilesOfDumpFolderTarget(String path);

    void dumpDatabaseToFile(String outputPath);

    void deleteAllFilesOfDumpFolder(String path);
}

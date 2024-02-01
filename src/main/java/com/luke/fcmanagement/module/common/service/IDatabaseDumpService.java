package com.luke.fcmanagement.module.common.service;

import java.io.IOException;

public interface IDatabaseDumpService {
    void dumpDatabaseToFile(String outputPath) throws IOException, InterruptedException;
    void deleteAllFilesOfDumpFolder(String path) throws IOException;
}

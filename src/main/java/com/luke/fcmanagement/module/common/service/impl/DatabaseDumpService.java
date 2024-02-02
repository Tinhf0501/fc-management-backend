package com.luke.fcmanagement.module.common.service.impl;

import com.luke.fcmanagement.exception.DumpDatabaseFailException;
import com.luke.fcmanagement.module.common.service.IDatabaseDumpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class DatabaseDumpService implements IDatabaseDumpService {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    private final ResourceLoader resourceLoader;

    @Override
    public void dumpDatabaseToFileTarget(String outputPath) {
        try {
            long start = System.currentTimeMillis();
            log.info("[START] dump database to folder with path target: {}", outputPath);
            processDump(outputPath);
            long end = System.currentTimeMillis();
            log.info("[END] dump database to folder with path target : {} after {} ms", outputPath, end - start);
        } catch (Exception e) {
            log.error("[ERR] dump database to target {} get exception: {}", outputPath, e.getMessage());
        }
    }

    public void deleteAllFilesOfDumpFolderTarget(String path) {
        try {
            long start = System.currentTimeMillis();
            log.info("[START] delete all file of folder with path target : {}", path);
            // * Get the Resource for the specified folder path
            Resource resource = resourceLoader.getResource(path);

//        Path directoryPathAbsolute = resource.getFile().toPath();
//        List<String> fileNames = new ArrayList<>();
//        // Use DirectoryStream to iterate over files in the directory
//        try (DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(directoryPathAbsolute)) {
//            for (Path path1 : directoryStream) {
//                // Extract file name from path and add to the list
//                fileNames.add(path1.getFileName().toString());
//            }
//        }
//        log.info("list file Name = {}", fileNames);

            // * Convert the Resource to a File
            File folder = resource.getFile();
            if (folder.exists() && folder.isDirectory()) {
                deleteFile(folder);
            } else {
                log.error("Invalid folder path or folder does not exist: {}", path);
            }
            long end = System.currentTimeMillis();
            log.info("[END] delete all file of folder with path target : {} after {} ms", path, end - start);
        } catch (Exception e) {
            log.error("[ERR] delete file in folder path : {} get exception {}", path, e.getMessage());
        }

    }

    @Override
    public void dumpDatabaseToFile(String outputPath) {
        try {
            long start = System.currentTimeMillis();
            log.info("[START] dump database to folder with path : {}", outputPath);
            processDump(outputPath);
            long end = System.currentTimeMillis();
            log.info("[END] dump database to folder with path : {} after {} ms", outputPath, end - start);
        } catch (Exception e) {
            log.error("[ERR] dump database to {} get exception: {}", outputPath, e.getMessage());
        }

    }

    @Override
    public void deleteAllFilesOfDumpFolder(String path) {
        try {
            long start = System.currentTimeMillis();
            log.info("[START] delete all file of folder with path : {}", path);

            // * Convert the Resource to a File
            File folder = new File(path);
            if (folder.exists() && folder.isDirectory()) {
                deleteFile(folder);
            } else {
                log.error("Invalid folder path or folder does not exist: {}", path);
            }
            long end = System.currentTimeMillis();
            log.info("[END] delete all file of folder with path : {} after {} ms", path, end - start);
        } catch (Exception e) {
            log.error("[ERR] delete file in folder path : {} get exception {}", path, e.getMessage());
        }
    }

    private void deleteFile(File folder) {
        try {
            // * Check if the path points to a directory
            if (folder.listFiles().length > 0) {
                log.info("Folder has: {} files", folder.listFiles().length);
                // * List all files in the directory
                File[] files = folder.listFiles();
                // * Delete each file
                if (files != null) {
                    for (File file : files) {
                        Files.delete(file.toPath());
//                    if (file.isFile() && !file.delete()) {
//                        throw new IOException("Failed to delete file: " + file.getAbsolutePath());
//                    }
                    }
                }
            } else {
                log.info("The provided path folder has no file");
            }
        } catch (Exception e) {
            log.error("[ERR] Failed to delete file in folder path: {}", folder.getAbsolutePath());
        }
    }

    private String getCommand(String outputPath) {
        return String.format("mysqldump -u %s -p%s %s > %s",
                dbUsername, dbPassword, extractDbNameFromUrl(), outputPath);
    }

    private void processDump(String outputPath) throws IOException, InterruptedException {
        // * Thực hiện dump cơ sở dữ liệu MySQL vào tệp
        String command = getCommand(outputPath);
        log.info("command : {}", command);
        // * Sử dụng ProcessBuilder để thực hiện command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
//        Process process = Runtime.getRuntime().exec(command);
        boolean exitCode = process.waitFor(60, TimeUnit.SECONDS); // * Timeout after 60 seconds

        if (!exitCode) {
            throw new DumpDatabaseFailException("Failed to dump the database.");
        }
    }

    private String extractDbNameFromUrl() {
        // * Trích xuất tên cơ sở dữ liệu từ URL kết nối
        String[] urlParts = dbUrl.split("/");
        String[] dbName = urlParts[urlParts.length - 1].split("\\?");
        return dbName[0];
    }
}

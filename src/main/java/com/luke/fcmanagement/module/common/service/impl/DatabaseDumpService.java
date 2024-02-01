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
    public void dumpDatabaseToFile(String outputPath) throws IOException, InterruptedException {
        // Thực hiện dump cơ sở dữ liệu MySQL vào tệp
        String command = String.format("mysqldump -u%s -p%s %s > %s",
                dbUsername, dbPassword, extractDbNameFromUrl(), outputPath);

        // Sử dụng ProcessBuilder để thực hiện command
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        boolean exitCode = process.waitFor(60, TimeUnit.SECONDS); // Timeout after 60 seconds

        if (!exitCode) {
            throw new DumpDatabaseFailException("Failed to dump the database.");
        }
    }

    private String extractDbNameFromUrl() {
        // Trích xuất tên cơ sở dữ liệu từ URL kết nối
        String[] urlParts = dbUrl.split("/");
        return urlParts[urlParts.length - 1];
    }

    public void deleteAllFilesOfDumpFolder(String path) throws IOException {
        // Get the Resource for the specified folder path
        Resource resource = resourceLoader.getResource("classpath:");

        // Convert the Resource to a File
        File folder = resource.getFile();
        log.info("folder: {}", folder.listFiles().length);
        // Check if the path points to a directory
//        if (folder.isDirectory()) {
//            // List all files in the directory
//            File[] files = folder.listFiles();
//            log.info("has file: {}", files.length);
//            // Delete each file
//            if (files != null) {
//                for (File file : files) {
//                    log.info("has file: {}", files.length);
//                    if (file.isFile()) {
//
//                        if (!file.delete()) {
//                            throw new IOException("Failed to delete file: " + file.getAbsolutePath());
//                        }
//                    }
//                }
//            }
//        } else {
//            throw new IllegalArgumentException("The provided path is not a directory: " + path);
//        }
    }
}

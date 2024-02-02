package com.luke.fcmanagement.module.common.job;

import com.luke.fcmanagement.module.common.service.impl.DatabaseDumpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseDumpJob {
    @Value("${database.dump.target_path}")
    private String dumpTargetPath;

    @Value("${database.dump.absolute_path}")
    private String dumpAbsolutePath;

    private final DatabaseDumpService databaseDumpService;

//    @Scheduled(cron = "${database.job}")
    public void dumpDatabase() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            dumpAbsolutePath(timestamp);
        } catch (Exception e) {
            log.error("[ERR] dump database err : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void dumpTargetPath(String timestamp) {
        String outputPath = dumpTargetPath + "backup_" + timestamp + ".sql";
        databaseDumpService.deleteAllFilesOfDumpFolderTarget(dumpTargetPath);
        databaseDumpService.dumpDatabaseToFileTarget(outputPath);
    }

    private void dumpAbsolutePath(String timestamp) {
        String outputPath = dumpAbsolutePath + "backup_" + timestamp + ".sql";
        databaseDumpService.deleteAllFilesOfDumpFolder(dumpAbsolutePath);
        databaseDumpService.dumpDatabaseToFile(outputPath);
    }
}

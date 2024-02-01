package com.luke.fcmanagement.module.common.job;

import com.luke.fcmanagement.module.common.service.impl.DatabaseDumpService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DatabaseDumpJob {
    @Value("${database.dump.path}")
    private String dumpPath;

    private final DatabaseDumpService databaseDumpService;

    @Scheduled(cron = "0 16 11 * * ?") // Run the job daily at
    public void dumpDatabase() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            String outputPath = dumpPath + "backup_" + timestamp + ".sql";
//            databaseDumpService.deleteAllFilesOfDumpFolder(dumpPath);
//            databaseDumpService.dumpDatabaseToFile(outputPath);

            System.out.println("Database dumped to: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

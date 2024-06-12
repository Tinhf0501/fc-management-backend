package com.luke.fcmanagement.module.job.handler;

import com.luke.fcmanagement.config.LocalSaverFileConfig;
import com.luke.fcmanagement.module.resource.IResourceRepository;
import com.luke.fcmanagement.module.resource.file.impl.LocalFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
@ConditionalOnProperty(prefix = "job.clean-resource", value = "active", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class CleanResourceFolderJob {
    private final LocalSaverFileConfig localSaverFileConfig;
    private final IResourceRepository resourceRepository;
    private final LocalFileService localFileService;

    @Scheduled(cron = "${job.clean-resource.time}")
    private void processClean() {
        final String localAbsolutePath = this.localSaverFileConfig.getAbsolutePath();
        try {
            log.info("[START] job clean folder local = {}", localAbsolutePath);
            List<String> filePathsLocal = getAllFilePaths(localAbsolutePath);
            List<String> allPathsOnDB = getAllPathsOnDb();
            List<String> listPathsDelete = getAllFilesDelete(filePathsLocal, allPathsOnDB);
            if (listPathsDelete.isEmpty()) {
                log.info("Folder is clean !!!!");
            } else {
                listPathsDelete.forEach(path -> {
                    log.info("Delete local file with path = {}", path);
                    localFileService.deleteFile(path);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("[END] job clean folder local = {}", localAbsolutePath);
        }
    }

    private List<String> getAllFilePaths(String folderPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            return paths
                    .filter(Files::isRegularFile) // Filter out directories
                    .map(Path::toAbsolutePath)    // Convert to absolute path
                    .map(Path::toString)          // Convert to string
                    .toList();
        }
    }

    private List<String> getAllPathsOnDb() {
        List<String> allPathsOnDB = resourceRepository.getAllPath();
        return allPathsOnDB.stream()
                .map(p -> p.replace(this.localSaverFileConfig.getHost(), this.localSaverFileConfig.getAbsolutePath()).replace("/", File.separator))
                .toList();
    }

    private List<String> getAllFilesDelete(List<String> listLocalPaths, List<String> listPathsDb) {
        return listLocalPaths.stream().filter(p -> !listPathsDb.contains(p))
                .toList();
    }

}

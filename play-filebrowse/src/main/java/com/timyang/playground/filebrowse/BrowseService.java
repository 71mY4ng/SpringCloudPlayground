package com.timyang.playground.filebrowse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BrowseService {

    public List<FileInfo> listDir(String pathStr) throws IOException {

        String fuzzyFilename = null;
        if (pathStr.contains("*")) {
            int fileNameStart = pathStr.lastIndexOf(File.separatorChar) + 1;
            fuzzyFilename = pathStr.substring(fileNameStart);
            pathStr = pathStr.substring(0, fileNameStart);
        }
        final Path dir = Paths.get(pathStr);

        final List<Path> files = listFolderWithFuzzy(dir, fuzzyFilename);
        List<FileInfo> fileInfos = new ArrayList<>(files.size());

        log.info("> ls -la {}", pathStr);
        for (Path file : files) {

            try {
                fileInfos.add(makeFileInfo(file));
            } catch (NoSuchFileException nsfEx) {
                log.error("[error]: [{}] : {}", nsfEx.getClass().getSimpleName(),
                        nsfEx.getMessage() + " no such file or directory");
            }
        }

        log.info("listed {} files in {}", files.size(), pathStr);
        return fileInfos;
    }

    private FileInfo makeFileInfo(Path file) throws IOException {

        final PosixFileAttributes attrs = Files.readAttributes(file, PosixFileAttributes.class);
        final String perms = PosixFilePermissions.toString(attrs.permissions());

        final FileInfo.FileInfoBuilder builder = FileInfo.builder()
                .owner(attrs.owner().getName())
                .group(attrs.group().getName())
                .size(attrs.size())
                .lastModified(LocalDateTime.ofInstant(attrs.lastModifiedTime().toInstant(), ZoneId.systemDefault()));
        if (Files.isSymbolicLink(file)) {
            final Path realFile = Files.readSymbolicLink(file);

            return builder.fileName(String.format("%s -> %s", file.getFileName(), realFile.toAbsolutePath()))
                    .permissions(perms)
                    .build();
        } else {
            return builder.fileName(file.getFileName().toString())
                    .permissions(perms)
                    .build();
        }
    }

    private List<Path> listFolderWithFuzzy(Path dir, String fuzzyName) throws IOException {

        if (StringUtils.isNotBlank(fuzzyName)) {
            List<Path> files = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, fuzzyName)) {
                for (Path entry : stream) {
                    files.add(entry);
                }
            }
            return files;
        } else {
            final List<Path> files = Files.list(dir).collect(Collectors.toList());

            files.add(dir.resolve(Paths.get(".")));
            files.add(dir.resolve(Paths.get("..")));

            return files;
        }
    }

    private static String type(PosixFileAttributes attrs) {

        if (attrs.isSymbolicLink()) {
            return "l";
        }
        if (attrs.isDirectory()) {
            return "d";
        }

        return "-";
    }
}

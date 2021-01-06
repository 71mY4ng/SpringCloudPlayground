package com.timyang.playground.filebrowse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FileInfo {
    private String fileName;
    private LocalDateTime lastModified;
    private String permissions;
    private String owner;
    private String group;
    private long size;
}

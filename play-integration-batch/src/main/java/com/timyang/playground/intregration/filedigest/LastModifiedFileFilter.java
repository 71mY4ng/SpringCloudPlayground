package com.timyang.playground.intregration.filedigest;

import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.springframework.integration.file.filters.AbstractFileListFilter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LastModifiedFileFilter extends AbstractFileListFilter<File> {
    private final Map<String, Long> files = new HashMap<>();
    private final Object monitor = new Object();

    @Override
    public boolean accept(File file) {
        synchronized (this.monitor) {
            final Long preModifiedTime = files.put(file.getName(), file.getAbsoluteFile().lastModified());

            return preModifiedTime == null || preModifiedTime != file.getAbsoluteFile().lastModified();
        }
    }
}

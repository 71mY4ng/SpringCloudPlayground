package com.timyang.playground.filebrowse;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/file-browser")
public class BrowseController {


    private final BrowseService browseService;

    @Autowired
    public BrowseController(BrowseService browseService) {
        this.browseService = browseService;
    }

    @GetMapping("/ls")
    @Validated
    public List<FileInfo> ls(@RequestParam @NonNull String path) throws IOException {
        log.info("req for ls {}", path);

        final Stopwatch started = Stopwatch.createStarted();
        final List<FileInfo> fileInfos = browseService.listDir(path);

        started.stop();
        log.info("list {} for {}ms", path, started.elapsed(TimeUnit.MILLISECONDS));

        return fileInfos;
    }
}

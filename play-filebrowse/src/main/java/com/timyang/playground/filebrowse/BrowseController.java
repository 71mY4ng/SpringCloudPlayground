package com.timyang.playground.filebrowse;

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

@RestController
@RequestMapping("/")
@Slf4j
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

        return browseService.listDir(path);
    }
}

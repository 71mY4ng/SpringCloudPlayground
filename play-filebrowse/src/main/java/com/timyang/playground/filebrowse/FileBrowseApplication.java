package com.timyang.playground.filebrowse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {AllMappingController.class})
})
@SpringBootApplication
public class FileBrowseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileBrowseApplication.class, args);
    }
}

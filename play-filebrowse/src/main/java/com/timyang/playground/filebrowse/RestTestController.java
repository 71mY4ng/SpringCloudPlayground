package com.timyang.playground.filebrowse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/rest-test")
public class RestTestController {

    @GetMapping(value = "/greeting/1/{bizDate}/{entityNo}/{versionNo}", produces = "text/csv")
    ResponseEntity<String> greeting(
            @PathVariable String bizDate,
            @PathVariable String entityNo,
            @PathVariable String versionNo
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body("hello");
    }

//    @GetMapping(value = "/greeting/1/{bizDate}/{entityNo}")
//    ResponseEntity<StreamingResponseBody> greeting(
//            @PathVariable String bizDate,
//            @PathVariable String entityNo,
//            final HttpServletResponse response
//    ) {
//        response.setContentType("text/csv");
//        response.setHeader(
//                "Content-Disposition",
//                "attachment;filename=sample.csv");
//
//        StreamingResponseBody stream = out -> {
//
//        };
//
//    }
}

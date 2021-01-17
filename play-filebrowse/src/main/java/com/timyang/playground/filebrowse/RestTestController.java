package com.timyang.playground.filebrowse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @GetMapping(value = "/download")
    ResponseEntity<StreamingResponseBody> download(
            final HttpServletResponse response
    ) throws IOException {
        response.setContentType("text/csv");
//        response.setHeader(
//                "Content-Disposition",
//                "attachment;filename=sample.csv");

        final InputStream is = Files.newInputStream(Paths.get("/home/timyang/Music/test4000file/file3821.bin"));

        StreamingResponseBody stream = os -> {

            byte[] data = new byte[2048];
            int read = 0;
            while ((read = is.read(data)) >= 0) {
                os.write(data, 0, read);
            }
            os.flush();
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setConnection("close");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .headers(headers)
                .body(stream);
    }

}

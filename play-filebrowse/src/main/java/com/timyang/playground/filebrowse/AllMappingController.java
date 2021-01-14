package com.timyang.playground.filebrowse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * fallback all unmapping url
 */
@Slf4j
@RestController
public class AllMappingController {

    @Data
    static class CommonResponse {
        private String code;
        private String msg;

        public CommonResponse(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    private static final CommonResponse DEFAULT = new CommonResponse("100", "finded");

    @RequestMapping(value = "**", method = RequestMethod.GET)
    ResponseEntity<CommonResponse> allGet() {
        log.info("requested allget");
        return ResponseEntity.accepted().body(DEFAULT);
    }

    @RequestMapping(value = "**", method = RequestMethod.POST)
    ResponseEntity<CommonResponse> allPost() {
        log.info("requested allpost");

        return ResponseEntity.accepted().body(DEFAULT);
    }
}

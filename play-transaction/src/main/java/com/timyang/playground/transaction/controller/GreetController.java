package com.timyang.playground.transaction.controller;

import com.timyang.playground.transaction.dao.TokenRecordRepository;
import com.timyang.playground.transaction.entitys.TokenRecord;
import com.timyang.playground.transaction.util.IdGenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Api(value = "Greet and user token", tags = "token")
@RestController
@RequestMapping("/greet")
public class GreetController {

    private static final Logger log = LoggerFactory.getLogger(GreetController.class);

    private final IdGenService idGenService;

    private final TokenRecordRepository redisTokenRecordRepo;

    @Autowired
    public GreetController(IdGenService idGenService,
                           TokenRecordRepository redisTokenRecordRepository) {
        this.idGenService = idGenService;
        this.redisTokenRecordRepo = redisTokenRecordRepository;
    }

    @GetMapping(value = "/greeting")
    @ApiOperation(value = "greeting", notes = "greeting and get token")
    public String greeting(@RequestParam String name) {

        log.debug("greeting transaction comes from {}", name);

        Optional<TokenRecord> record = Optional.ofNullable(redisTokenRecordRepo.getTokenRecordByUsername(name));

        if (record.isPresent()) {
            TokenRecord tokenRecord = record.get();

            return "Hello! Dear " + name + ", you already have token: "
                    + tokenRecord.getToken() +
                    " at " + tokenRecord.getGenTime().format(DateTimeFormatter.ISO_DATE_TIME);
        }

        LocalDateTime createTime = LocalDateTime.now();
        String token = idGenService.getId(createTime);

        redisTokenRecordRepo.createTokenRecord(new TokenRecord(name, token, createTime));

        return "Hello! Dear " + name + ", you get token: "
                + token + " at " + createTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @PostMapping(value = "/update")
    @ApiOperation("updateToken")
    public TokenRecord updateToken(@RequestParam String name) {
        log.debug("{} request for an update of the token", name);

        LocalDateTime createTime = LocalDateTime.now();
        String token = idGenService.getId(createTime);

        final TokenRecord neoToken = new TokenRecord(name, token, createTime);
        redisTokenRecordRepo.updateTokenRecordByUsername(name, neoToken);

        return neoToken;
    }

    @GetMapping(value = "/list")
    @ApiOperation("listTokenRecord")
    public List<TokenRecord> list() {

        log.debug("list tokenRecord");

        return Collections.emptyList();
    }
}

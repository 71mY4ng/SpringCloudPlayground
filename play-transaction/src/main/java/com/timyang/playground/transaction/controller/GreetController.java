package com.timyang.playground.transaction.controller;

import com.timyang.playground.transaction.dao.TokenRecordRepository;
import com.timyang.playground.transaction.entitys.TokenRecord;
import com.timyang.playground.transaction.util.IdGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController("greet")
public class GreetController {

    private final IdGenService idGenService;

    private final TokenRecordRepository redisTokenRecordRepo;

    @Autowired
    public GreetController(IdGenService idGenService,
                           TokenRecordRepository redisTokenRecordRepository) {
        this.idGenService = idGenService;
        this.redisTokenRecordRepo = redisTokenRecordRepository;
    }

    @RequestMapping
    public String greeting(@RequestParam String name) {
        LocalDateTime createTime = LocalDateTime.now();
        String token = idGenService.getId(createTime);

        redisTokenRecordRepo.createTokenRecord(new TokenRecord(name, token, createTime));

        return "Hello! Dear " + name + ", you get token: "
                + token + " at " + createTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}

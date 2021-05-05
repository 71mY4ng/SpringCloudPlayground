package com.timyang.playground.api.controller;

import com.timyang.playground.api.controller.dto.AuthRequest;
import com.timyang.playground.api.controller.dto.AuthResponse;
import com.timyang.playground.api.controller.dto.RefreshTokenRequest;
import com.timyang.playground.api.dao.TokenRecordRepository;
import com.timyang.playground.api.entitys.TokenRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Api(value = "auth api", tags = "token")
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

    private final AuthService authService;
    private final TokenRecordRepository redisTokenRecordRepo;

    @Autowired
    public AuthApiController(
            AuthService authServiceImpl,
            TokenRecordRepository redisTokenRecordRepository) {
        this.authService = authServiceImpl;
        this.redisTokenRecordRepo = redisTokenRecordRepository;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "login", notes = "get authenticated and token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest req) {

        log.debug("login|{}", req.getUsername());

        return ResponseEntity.ok(AuthResponse.parse(authService.login(req)));

    }

    @PostMapping(value = "/refreshToken")
    @ApiOperation("refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest req) {
        log.debug("refreshToken|{}", req.getUsername());

        return ResponseEntity.ok(AuthResponse.parse(authService.refreshToken(req)));
    }

    @GetMapping(value = "/list")
    @ApiOperation("listTokenRecord")
    public List<TokenRecord> list() {

        log.debug("list tokenRecord");

        return Collections.emptyList();
    }
}

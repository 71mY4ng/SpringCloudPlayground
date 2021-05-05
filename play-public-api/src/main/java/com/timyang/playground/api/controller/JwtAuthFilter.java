package com.timyang.playground.api.controller;

import com.timyang.playground.api.dao.TokenRecordRepository;
import com.timyang.playground.api.entitys.TokenRecord;
import com.timyang.playground.api.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.timyang.playground.api.util.JwtTokenUtils.TOKEN_HEADER;
import static com.timyang.playground.api.util.JwtTokenUtils.TOKEN_PREFIX;

@Slf4j
public class JwtAuthFilter extends BasicAuthenticationFilter {

    private final TokenRecordRepository tokenRecordRepository;

    public JwtAuthFilter(AuthenticationManager authenticationManager,
                         TokenRecordRepository redisTokenRecordRepository) {
        super(authenticationManager);
        this.tokenRecordRepository = redisTokenRecordRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        final String token = request.getHeader(TOKEN_HEADER);
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

        String tokenVal = token.replace(TOKEN_PREFIX, "");
        TokenRecord tokenRec = tokenRecordRepository.getTokenRecordByUsername(JwtTokenUtils.getUsername(tokenVal));
        String previousToken = tokenRec.getToken();

        if (!token.equals(previousToken)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        final UsernamePasswordAuthenticationToken authentication = JwtTokenUtils.getAuthentication(tokenVal);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}

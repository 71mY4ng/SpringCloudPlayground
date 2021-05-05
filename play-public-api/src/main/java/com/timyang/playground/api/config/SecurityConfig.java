package com.timyang.playground.api.config;

import com.timyang.playground.api.controller.exception.JwtAccessDeniedHandler;
import com.timyang.playground.api.controller.exception.JwtAuthEntryPoint;
import com.timyang.playground.api.controller.JwtAuthFilter;
import com.timyang.playground.api.dao.TokenRecordRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.timyang.playground.api.util.JwtTokenUtils.SWAGGER_WHITELIST;
import static com.timyang.playground.api.util.JwtTokenUtils.SYSTEM_WHITELIST;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenRecordRepository tokenRecordRepository;

    public SecurityConfig(TokenRecordRepository redisTokenRecordRepository) {
        this.tokenRecordRepository = redisTokenRecordRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf().disable()
                .authorizeRequests()
                // white list rule start
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, SYSTEM_WHITELIST).permitAll()
                .anyRequest().authenticated()
                // white list rule end
                .and()
                .addFilter(new JwtAuthFilter(authenticationManager(), tokenRecordRepository))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler())
        ;

    }
}

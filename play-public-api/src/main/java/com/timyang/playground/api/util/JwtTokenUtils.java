package com.timyang.playground.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenUtils {

    /**
     * JWT签名密钥
     */
    public static final String JWT_SECRET_KEY = "dv_;#zAOoXsD.L(tL_9>G=!,y37dc,[Y5yps@KBTU!f6U2c~1Xh<C4$jx+IM:.[";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    // Swagger WHITELIST
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            "/doc.html",
    };

    // System WHITELIST
    public static final String[] SYSTEM_WHITELIST = {
            "/api/auth/login",
            "/api/auth/signup"
    };

    private static final byte[] SECRET_BINS = DatatypeConverter.parseBase64Binary(JWT_SECRET_KEY);
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_BINS);

    public static String createToken(String id, String username, List<String> roles) {

        long expiration = 60 * 60L;
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);

        return TOKEN_PREFIX + Jwts.builder()
                .setHeaderParam("type", TOKEN_TYPE)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim("rol", String.join(",", roles))
                .setId(id)
                .setIssuer("MoodyTor")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
    }

    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaims(token);

        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String username = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(username, token, authorities);
    }

    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {

        return Arrays.stream(claims.get("rol", String.class).split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

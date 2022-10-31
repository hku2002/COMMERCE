package com.commerce.global.common.token;

import com.commerce.global.common.exception.InvalidTokenAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenManager implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidInSeconds;
    private final long REFRESH_TOKEN_TIME = 1000L * 60 * 60 * 24 * 7; // 7일
    private Key key;

    public JwtTokenManager(@Value("${jwt.secret}") String secret, @Value("${jwt.token-validity-in-seconds}") long tokenValidInSeconds) {
        this.secret = secret;
        this.tokenValidInSeconds = tokenValidInSeconds;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Authentication authentication) {
        return generateToken(authentication, getAuthorities(authentication), tokenValidInSeconds);
    }
    
    public String createRefreshToken(Authentication authentication) {
        return generateToken(authentication, getAuthorities(authentication), REFRESH_TOKEN_TIME);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new InvalidTokenAuthenticationException("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenAuthenticationException("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalIdentifierException e) {
            throw new InvalidTokenAuthenticationException("잘못된 JWT 토큰입니다.");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenAuthenticationException("만료된 JWT 토큰입니다.");
        }
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
    }

    private String generateToken(Authentication authentication, String authorities, long expirationTime) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date((new Date()).getTime() + expirationTime))
                .setIssuedAt(new Date())
                .compact();
    }

}

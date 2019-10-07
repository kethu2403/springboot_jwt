package com.hexaware.jumbo.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * JwtTokenUtil.
 */
@Component
public class JwtTokenUtil {
    /**
     * jwtsecretkey.
    */
    @Value("${JWT_SECRETKEY:defaultValue}")
    private String jwtSecret;

    /**
     * jwtAccessTokenExpiryTime.
    */
    @Value("${JWT_ACCESSTOKEN_EXPIRY_TIME_IN_HOURS:defaultValue}")
    private String jwtAccessTokenExpiryTime;

    /**
     * @return getUsername from jwt token.
     * @param token token.
     */
    public String getUsernameFromToken(final String token) {
        return this.getClaimsFromToken(token).getSubject();
    }
    /**
     * @return expirytime.
     * @param token token.
     */
    public Date getExpirationTimeFromToken(final String token) {
        return this.getClaimsFromToken(token).getExpiration();
    }
    /**
     * @return claims.
     * @param token token.
     */
    private Claims getClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    /**
     * @return tokenexpiry.
     * @param token token.
     */
    private Boolean isTokenExpired(final String token) {
        Date expiryDate = this.getExpirationTimeFromToken(token);
        return expiryDate.before(new Date());
    }
    /**
     * @param username use.
     * @return jwtAccessToken.
     */
    public String generateAccessToken(final String username) {
        String jwtAccessToken = "";
        Long expiryTimeInHours = TimeUnit.MILLISECONDS.convert(
            Long.parseLong(jwtAccessTokenExpiryTime), TimeUnit.HOURS);
        Date expiryTimeForAccessToken = new Date(System.currentTimeMillis() + expiryTimeInHours);
        jwtAccessToken = Jwts.builder().setSubject(username)
                .claim("role", "admin").claim("partyname", "jumbo").setIssuer("jumbo")
                .setIssuedAt(new Date()).setExpiration(expiryTimeForAccessToken)
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        return jwtAccessToken;
    }
    /**
     * @return validation.
     * @param userDetails userDetails.
     * @param token token.
     */
    public Boolean validateToken(final String token, final UserDetails userDetails) {
        String username = this.getUsernameFromToken(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }
}

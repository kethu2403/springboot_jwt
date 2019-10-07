package com.hexaware.jumbo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hexaware.jumbo.util.JwtTokenUtil;
import com.hexaware.jumbo.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * JwtFilter.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    /**
     * userService.
     */
    @Autowired
    private UserService userService;
    /**
     * jwtTokenUtil.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Request filter abstract method.
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            token = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                LOGGER.info("Unable to get JWT Token");
            } catch (final ExpiredJwtException e) {
                LOGGER.info("Expired JWT Token:::" + e.getMessage());
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt Token Expired");
            }
        } else {
            LOGGER.info("Invalid Jwt Token");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

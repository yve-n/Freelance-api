package com.cda.freely.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //    filtre de journalisation qui utilise Logback
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * extract token from the request header.
     * check if token starts with Bearer
     *
     * @param request
     * @return token
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            logger.debug("JWT token from request: {}", jwt);
            if(StringUtils.hasText(jwt)){
                if(tokenProvider.validateToken(jwt)){
                    String username = tokenProvider.getUsernameFromJWT(jwt);
                    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                    logger.debug("JWT token is valid for user: {}", username);
                    String roles = (String) tokenProvider.getTokenBody(jwt).get("roles");
                    List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
                            .map(role -> new SimpleGrantedAuthority(role))
                            .collect(Collectors.toList());
                    logger.debug("roles : {}", authorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else{
                    logger.debug("JWT token is invalid for user: {}");
                    throw new BadCredentialsException("Invalid JWT token");
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex.getMessage());
        }
            logger.info("{} {} {} {} {}ms", request.getMethod(), request.getRequestURI(), response.getStatus(), request.getRemoteAddr());
            filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String requestUri = request.getRequestURI();

        // Vérifier si l'URL correspond à un endpoint public
        List<RequestMatcher> publicMatchers = Arrays.asList(
                new AntPathRequestMatcher("/"),
                new AntPathRequestMatcher("/contact"),
                new AntPathRequestMatcher("/family"),
                new AntPathRequestMatcher("/family/all")
                // Ajoutez d'autres patterns pour les endpoints publics si nécessaire
        );

        for (RequestMatcher matcher : publicMatchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }

        // Vérifier si l'URL correspond à un endpoint public par méthode HTTP
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        List<RequestMatcher> publicMethodMatchers = Arrays.asList(
                new AntPathRequestMatcher("/family"),
                new AntPathRequestMatcher("/family/all")
                // Ajoutez d'autres patterns pour les endpoints publics par méthode HTTP si nécessaire
        );

        for (RequestMatcher matcher : publicMethodMatchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }

        return false;
    }

}

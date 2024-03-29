package com.cda.freely.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * CustomAccessDeniedHandler permet de renvoyer un message personnalisé à l'utilisateur
 * en cas d'accès refusé
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException {
        response.setContentType("application/json");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "{\"message\" : \"Vous ne pouvez pas accéder à cette page\"} " + exc.getMessage());
    }
}

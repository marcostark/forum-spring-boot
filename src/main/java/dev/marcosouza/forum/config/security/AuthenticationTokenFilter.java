package dev.marcosouza.forum.config.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    public AuthenticationTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);
        boolean isValid = tokenService.isTokenValid(token);

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if( token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}

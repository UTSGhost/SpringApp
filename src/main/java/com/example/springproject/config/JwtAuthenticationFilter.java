package com.example.springproject.config;

import com.example.springproject.entity.User;
import com.example.springproject.service.JwtService;
import com.example.springproject.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * filters users without jwt Token (not logged in)
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        // jwt is in Header
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            // not lo
            filterChain.doFilter(request, response);
            return;
        }
        // raw jwt String
        String usrJwt = header.substring(7);
        String usrEmail = jwtService.extractEmail(usrJwt);
        // skip if email is null or user already authenticated
        if (usrEmail == null || SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request, response);
            return;
        }
        // skip if user is null
        User user = userService.getUserByEmail(usrEmail).orElse(null);
        if (user == null){
            filterChain.doFilter(request, response);
            return;
        }
        // add role
        String role = user.getRole() != null ? user.getRole() : "ROLE_USER";
        // required for authToken
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

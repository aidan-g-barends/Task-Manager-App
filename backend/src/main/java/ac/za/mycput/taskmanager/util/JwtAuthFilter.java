package ac.za.mycput.taskmanager.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Long userId = jwtUtil.getUserId(token);
                String role = jwtUtil.getRole(token);

                request.setAttribute("userId", userId);
                request.setAttribute("role", role);
            } catch (Exception e) {
                // Invalid or expired token — leave request unauthenticated,
                // controllers that require auth will reject it themselves.
            }
        }

        filterChain.doFilter(request, response);
    }
}
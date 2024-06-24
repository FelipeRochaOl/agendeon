package br.com.agendaon.middleware;

import br.com.agendaon.user.UserModel;
import br.com.agendaon.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class UserMiddleware extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(@org.jetbrains.annotations.NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getServletPath().startsWith("/test")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        String authEncode = authorization.substring("Basic".length()).trim();
        byte[] authDecode = Base64.getDecoder().decode(authEncode);
        String auth = new String(authDecode);
        String[] credentials = auth.split(":");
        String email = credentials[0];
        String password = credentials[1];

        UserModel user = userService.findByEmail(email);
        if (user == null || !this.userService.isValidatePassword(password, user.getPassword())) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "User unauthorized");
            return;
        }

        request.setAttribute("userId", user.getId());
        filterChain.doFilter(request, response);
    }
}

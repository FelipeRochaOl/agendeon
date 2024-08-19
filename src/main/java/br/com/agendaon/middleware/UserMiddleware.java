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
import java.util.ArrayList;
import java.util.Base64;

@Component
public class UserMiddleware extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(@org.jetbrains.annotations.NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        if (this.isOpenUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        String email = "";
        String password = "";
        if (authorization != null) {
            String authEncode = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncode);
            String auth = new String(authDecode);
            String[] credentials = auth.split(":");
            email = credentials[0];
            password = credentials[1];
        }

        UserModel user = userService.findByEmail(email);
        if (user == null || !this.userService.isValidatePassword(password, user.getPassword())) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "User unauthorized");
            return;
        }

        request.setAttribute("userId", user.getId());
        request.setAttribute("email", user.getEmail());
        filterChain.doFilter(request, response);
    }

    protected Boolean isOpenUrl(String url) {
        ArrayList<String> routes = this.openRoutes();
        return routes.contains(url);
    }

    protected ArrayList<String> openRoutes() {
        ArrayList<String> routes = new ArrayList<>();
        routes.add("/login");
        routes.add("/register");
        routes.add("/logout");
        routes.add("/");
        return routes;
    }
}

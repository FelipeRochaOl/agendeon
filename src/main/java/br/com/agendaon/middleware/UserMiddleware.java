package br.com.agendaon.middleware;

import br.com.agendaon.auth.JwtService;
import br.com.agendaon.user.UserModel;
import br.com.agendaon.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class UserMiddleware extends OncePerRequestFilter {
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(
            @org.jetbrains.annotations.NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (this.isOpenUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authorization.substring(7);
            String userEmail = jwtService.extractUsername(jwt);
            request.setAttribute("token", jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            if (userEmail != null && authentication != null) {
                UserModel userModel = this.userService.getByEmail(userEmail);
                request.setAttribute("user", userModel);
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    protected Boolean isOpenUrl(String url) {
        ArrayList<String> routes = this.openRoutes();
        if (this.isEnabledRegexUrl("^\\/companies\\/[\\w-]+\\/[\\w-]+$", url)) return true;
        if (this.isEnabledRegexUrl("^\\/companies\\/[\\w-]+$", url)) return true;
        return routes.contains(url);
    }

    protected ArrayList<String> openRoutes() {
        ArrayList<String> routes = new ArrayList<>();
        routes.add("/auth/login");
        routes.add("/auth/signup");
        routes.add("/session/list");
        routes.add("/categories/list");
        routes.add("/companies/list");
        routes.add("/companies/filter");
        routes.add("/");
        return routes;
    }

    protected boolean isEnabledRegexUrl(String regex, String url) {
        return url.matches(regex);
    }
}
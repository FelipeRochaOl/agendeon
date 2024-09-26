package br.com.agendaon.auth;

import br.com.agendaon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public AuthPresenter authenticate(String email, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails user = this.userService.getByEmail(email);
        String jwtToken = jwtService.generateToken(user);
        long expirationIn = this.calculateExpirationTime();
        AuthPresenter auth = new AuthPresenter();
        auth.setToken(jwtToken);
        auth.setUsername(user.getUsername());
        auth.setExpiresIn(expirationIn);
        return auth;
    }

    private long calculateExpirationTime() {
        long expirationTime = jwtService.getExpirationTime();
        long now = System.currentTimeMillis();
        return now + expirationTime;
    }
}

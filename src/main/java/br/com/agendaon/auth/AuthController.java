package br.com.agendaon.auth;

import br.com.agendaon.response.ResponsePresenter;
import br.com.agendaon.user.UserDTO;
import br.com.agendaon.user.UserModel;
import br.com.agendaon.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponsePresenter<AuthPresenter>> login(@RequestBody AuthDTO authDTO) {
        try {
            AuthPresenter auth = this.authService.authenticate(authDTO.getEmail(), authDTO.getPassword());
            UserModel user = this.userService.getByEmail(authDTO.getEmail());
            auth.setIsBusiness(user.isBusiness());
            ResponsePresenter<AuthPresenter> response = new ResponsePresenter<>(true, auth);
            return ResponseEntity.ok(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponsePresenter<AuthPresenter>> create(@RequestBody AuthDTO authDTO) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(authDTO.getEmail());
            userDTO.setPassword(authDTO.getPassword());
            AuthPresenter auth = this.authService.authenticate(authDTO.getEmail(), authDTO.getPassword());
            ResponsePresenter<AuthPresenter> response = new ResponsePresenter<>(true, auth);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "User already exists")) {
                return this.login(authDTO);
            }
            ResponsePresenter<AuthPresenter> response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponsePresenter<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) new SecurityContextLogoutHandler().logout(request, response, authentication);
            return ResponseEntity.ok(new ResponsePresenter<>(true));
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }
}

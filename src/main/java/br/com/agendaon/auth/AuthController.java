package br.com.agendaon.auth;

import br.com.agendaon.response.ResponsePresenter;
import br.com.agendaon.user.UserDTO;
import br.com.agendaon.user.UserPresenter;
import br.com.agendaon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
            ResponsePresenter<AuthPresenter> response = new ResponsePresenter<>(true, auth);
            return ResponseEntity.ok(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponsePresenter<UserPresenter>> create(@RequestBody AuthDTO authDTO) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(authDTO.getEmail());
            userDTO.setPassword(authDTO.getPassword());
            userDTO.setCreatedAt(new Date());
            userDTO.setDeleted(false);
            UserPresenter newUser = this.userService.createUser(userDTO);
            ResponsePresenter<UserPresenter> response = new ResponsePresenter<>(true, newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "User already exists")) {
                return ResponseEntity.badRequest().body(new ResponsePresenter<>(false));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponsePresenter<String>> logout() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object credential = authentication.getCredentials();
            this.authService.logout(credential.toString());
            return ResponseEntity.ok(new ResponsePresenter<>(true));
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }
}

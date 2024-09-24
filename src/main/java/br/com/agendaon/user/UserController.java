package br.com.agendaon.user;

import br.com.agendaon.response.ResponsePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<ResponsePresenter<List<UserPresenter>>> findAll() {
        List<UserPresenter> users = this.userService.findAll();
        ResponsePresenter<List<UserPresenter>> response = new ResponsePresenter<>(true, users);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponsePresenter<UserPresenter>> findByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel currentUser = (UserModel) authentication.getPrincipal();
        UserPresenter user = this.userService.profile(currentUser);
        ResponsePresenter<UserPresenter> response = new ResponsePresenter<>(true, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<UserPresenter>> create(@RequestBody UserDTO userDTO) {
        try {
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

}

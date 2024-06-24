package br.com.agendaon.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserPresenter>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<UserPresenter> create(@RequestBody UserDTO userDTO) {
        try {
            UserPresenter newUser = this.userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            if (Objects.equals(e.getMessage(), "User already exists")) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

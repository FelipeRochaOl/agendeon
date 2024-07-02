package br.com.agendaon.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserPresenter> findAll() {
        List<UserModel> users = this.userRepository.findAll();
        List<UserPresenter> usersPresenter = new ArrayList<>();
        for (UserModel user : users) {
            UserPresenter userPresenter = new UserPresenter();
            userPresenter.setEmail(user.getEmail());
            userPresenter.setCreatedAt(user.getCreatedAt());
            usersPresenter.add(userPresenter);
        }
        return usersPresenter;
    }

    public UserModel findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public UserPresenter profile(String email) {
        UserModel user = this.findByEmail(email);
        return new UserPresenter(user);
    }

    public UserModel findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> null);
    }

    public UserPresenter createUser(UserDTO user) throws Exception {
        UserModel searchUser = this.userRepository.findByEmail(user.getEmail());
        if (searchUser != null) {
            throw new Exception("User already exists");
        }
        String encodedPassword = this.encryptPassword(user.getPassword());
        user.setPassword(encodedPassword);
        UserModel userModel = new UserModel();
        userModel.setEmail(user.getEmail());
        userModel.setPassword(encodedPassword);
        UserModel newUser = this.userRepository.save(userModel);
        UserPresenter userPresenter = new UserPresenter();
        userPresenter.setEmail(newUser.getEmail());
        return userPresenter;
    }

    public boolean isValidatePassword(String password, String hashPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hashPassword);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}

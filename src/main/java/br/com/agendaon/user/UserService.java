package br.com.agendaon.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            userPresenter.setId(user.getId());
            userPresenter.setEmail(user.getEmail());
            userPresenter.setCreatedAt(user.getCreatedAt());
            usersPresenter.add(userPresenter);
        }
        return usersPresenter;
    }

    public UserModel getByEmail(String email) {
        return this.userRepository.getByEmail(email);
    }

    public UserPresenter profile(UserModel user) {
        return new UserPresenter(user);
    }

    public UserModel findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> null);
    }

    public UserPresenter createUser(UserDTO user) throws Exception {
        Optional<UserModel> searchUser = this.userRepository.findByEmail(user.getEmail());
        if (!searchUser.isEmpty()) {
            throw new Exception("User already exists");
        }
        String encodedPassword = this.encryptPassword(user.getPassword());
        user.setPassword(encodedPassword);
        UserModel userModel = new UserModel();
        userModel.setEmail(user.getEmail());
        userModel.setPassword(encodedPassword);
        userModel.setBusiness(user.getIsBusiness());
        UserModel newUser = this.userRepository.save(userModel);
        UserPresenter userPresenter = new UserPresenter();
        userPresenter.setEmail(newUser.getEmail());
        return userPresenter;
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}

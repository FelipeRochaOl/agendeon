package br.com.agendaon.user;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserPresenter {
    private UUID id;
    private String email;
    @Setter
    public LocalDateTime createdAt;

    public UserPresenter() {
    }

    public UserPresenter(UserModel userModel) {
        this.id = userModel.getId();
        this.email = userModel.getEmail();
        this.createdAt = userModel.getCreatedAt();
    }
}

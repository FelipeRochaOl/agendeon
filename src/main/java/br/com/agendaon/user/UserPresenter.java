package br.com.agendaon.user;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class UserPresenter {
    @Setter
    public LocalDateTime createdAt;
    private String email;

    public String getCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(formatter);
    }
}

package br.com.agendaon.user;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String password;
    private Date createdAt;
    private Boolean deleted;
}

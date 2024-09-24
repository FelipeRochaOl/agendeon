package br.com.agendaon.auth;

import lombok.Data;

@Data
public class AuthDTO {
    private String email;
    private String password;
}

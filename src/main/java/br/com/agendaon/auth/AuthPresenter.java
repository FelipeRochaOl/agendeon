package br.com.agendaon.auth;

import lombok.Data;

@Data
public class AuthPresenter {
    private String token;
    private String username;
    private long expiresIn;
}
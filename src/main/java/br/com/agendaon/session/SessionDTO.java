package br.com.agendaon.session;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionDTO {
    private UUID code;
    private String name;
}

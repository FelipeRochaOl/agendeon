package br.com.agendaon.client;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientDTO {
    private String name;
    private String cpf;
    private Number age;
    private UUID userId;
}

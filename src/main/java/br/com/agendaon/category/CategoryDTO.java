package br.com.agendaon.category;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDTO {
    private UUID code;
    private UUID sessionCode;
    private String name;
}

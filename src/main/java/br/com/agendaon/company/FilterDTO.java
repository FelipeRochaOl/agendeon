package br.com.agendaon.company;

import lombok.Data;

import java.util.UUID;

@Data
public class FilterDTO {
    private String zip;
    private String city;
    private String neighborhood;
    private UUID session;
    private UUID category;
}

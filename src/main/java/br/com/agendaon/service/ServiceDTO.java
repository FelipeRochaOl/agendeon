package br.com.agendaon.service;

import lombok.Data;

import java.util.UUID;

@Data
public class ServiceDTO {
    private UUID code;
    private String description;
    private Double value;
    private Number duration;
}

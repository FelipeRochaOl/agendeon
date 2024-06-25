package br.com.agendaon.service;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "t_agon_services", schema = "AGENDEON")
public class ServiceModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double value;

    @Column()
    private Number duration;
}

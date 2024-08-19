package br.com.agendaon.session;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "t_agon_session", schema = "AGENDEON")
public class SessionModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID code;

    @Column(nullable = false)
    private String name;
}

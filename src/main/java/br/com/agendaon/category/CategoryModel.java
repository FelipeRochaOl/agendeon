package br.com.agendaon.category;

import br.com.agendaon.session.SessionModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "t_agon_category", schema = "AGENDEON")
public class CategoryModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID code;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    private SessionModel session;

    @Column(nullable = false)
    private String name;
}

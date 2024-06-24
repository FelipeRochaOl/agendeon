package br.com.agendaon.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_agon_users", schema = "AGENDEON")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column()
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @SoftDelete
    private boolean deleted;
}

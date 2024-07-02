package br.com.agendaon.client;

import br.com.agendaon.user.UserModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_agon_client", schema = "AGENDEON")
public class ClientModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private Number age;

    @OneToOne(cascade = CascadeType.PERSIST)
    private UserModel user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete
    private boolean deleted;

    public ClientModel() {
    }

    public ClientModel(ClientDTO clientDTO) {
        this.setName(clientDTO.getName());
        this.setCpf(clientDTO.getCpf());
        this.setAge(clientDTO.getAge());
    }
}

package br.com.agendaon.address;

import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name= "t_agon_address", schema = "AGENDEON")
public class AddressModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String street;

    @Column()
    private String houseNumber;

    @Column()
    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 8)
    private String zip;

    @Column(nullable = false)
    private String country;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete
    private boolean deleted;

    public AddressModel() {}

    public AddressModel(AddressDTO addressDTO) {
        this.street = addressDTO.getStreet();
        this.houseNumber = addressDTO.getNumber();
        this.complement = addressDTO.getComplement();
        this.neighborhood = addressDTO.getNeighborhood();
        this.city = addressDTO.getCity();
        this.state = addressDTO.getState();
        this.uf = addressDTO.getUf();
        this.zip = addressDTO.getZip();
        this.country = addressDTO.getCountry();
    }
}

package br.com.agendaon.company;

import br.com.agendaon.address.AddressModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_agon_company", schema = "AGENDEON")
public class CompanyModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String tradeName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private AddressModel address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete
    private boolean deleted;

    public CompanyModel() {
    }

    public CompanyModel(CompanyDTO companyDTO) {
        this.setCompanyName(companyDTO.getCompanyName());
        this.setTradeName(companyDTO.getTradeName());
        this.setCnpj(companyDTO.getCnpj());
    }
}

package br.com.agendaon.company;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyDTO {
    private UUID id;
    private String companyName;
    private String tradeName;
    private String cnpj;
    private UUID addressId;
    private UUID userId;
}

package br.com.agendaon.service;

import br.com.agendaon.company.CompanyModel;
import lombok.Data;

import java.util.UUID;

@Data
public class ServicePresenter {
    private UUID code;
    private String description;
    private Double value;
    private Number duration;
    private String formattedValue;
    private CompanyModel company;

    public ServicePresenter(ServiceModel serviceModel) {
        this.code = serviceModel.getCode();
        this.description = serviceModel.getDescription();
        this.value = serviceModel.getValue();
        this.duration = serviceModel.getDuration();
        this.company = serviceModel.getCompany();
        this.setFormattedValue();
    }

    private void setFormattedValue() {
        this.formattedValue = String.format("R$ %.2f", this.value);
    }
}

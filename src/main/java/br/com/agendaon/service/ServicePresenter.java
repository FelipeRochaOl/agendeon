package br.com.agendaon.service;

import lombok.Data;

import java.util.UUID;

@Data
public class ServicePresenter {
    private UUID code;
    private String description;
    private Double value;
    private Number duration;

    public ServicePresenter(ServiceModel serviceModel) {
        this.code = serviceModel.getCode();
        this.description = serviceModel.getDescription();
        this.value = serviceModel.getValue();
        this.duration = serviceModel.getDuration();
    }
}

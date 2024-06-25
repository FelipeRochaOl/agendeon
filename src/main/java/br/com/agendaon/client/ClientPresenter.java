package br.com.agendaon.client;

import lombok.Data;

@Data
public class ClientPresenter {
    private String name;
    private String cpf;
    private Number age;

    ClientPresenter(ClientModel clientModel) {
        this.name = clientModel.getName();
        this.age = clientModel.getAge();
        this.cpf = clientModel.getCpf();
    }
}

package br.com.agendaon.client;

import br.com.agendaon.user.UserPresenter;
import lombok.Data;

import java.util.UUID;

@Data
public class ClientPresenter {
    private UUID id;
    private String cpf;
    private String name;
    private Number age;
    private UserPresenter user;

    ClientPresenter(ClientModel clientModel) {
        this.id = clientModel.getId();
        this.name = clientModel.getName();
        this.age = clientModel.getAge();
        this.cpf = clientModel.getCpf();
        this.setUser(clientModel);
    }

    private void setUser(ClientModel clientModel) {
        this.user = new UserPresenter(clientModel.getUser());
    }
}

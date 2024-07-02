package br.com.agendaon.client;

import br.com.agendaon.user.UserPresenter;
import lombok.Data;

@Data
public class ClientPresenter {
    private String cpf;
    private String name;
    private Number age;
    private UserPresenter user;

    ClientPresenter(ClientModel clientModel) {
        this.name = clientModel.getName();
        this.age = clientModel.getAge();
        this.cpf = clientModel.getCpf();
        this.setUser(clientModel);
    }

    private void setUser(ClientModel clientModel) {
        this.user = new UserPresenter(clientModel.getUser());
    }
}

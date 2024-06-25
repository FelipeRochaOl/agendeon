package br.com.agendaon.client;

import br.com.agendaon.user.UserModel;
import br.com.agendaon.user.UserService;
import br.com.agendaon.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    public List<ClientPresenter> findAll() {
        List<ClientModel> clients = this.clientRepository.findAll();
        List<ClientPresenter> result = new ArrayList<>();
        clients.forEach(client -> result.add(new ClientPresenter(client)));
        return result;
    }

    public ClientPresenter me(UUID userId) {
        ClientModel client = this.clientRepository.findByUserId(userId).orElse(null);
        if (client == null) return null;
        return new ClientPresenter(client);
    }

    public ClientPresenter create(ClientDTO clientDTO, UUID userId) {
        try {
            UserModel user = this.userService.findById(userId);
            ClientModel clientModel = new ClientModel();
            clientModel.setDatabase(clientDTO);
            clientModel.setUser(user);
            ClientModel client = this.clientRepository.save(clientModel);
            return new ClientPresenter(client);
        } catch (Exception error) {
            System.out.println("Error create user " + error.getMessage());
            return null;
        }
    }

    public ClientPresenter update(ClientDTO clientDTO, UUID clientId) {
        try {
            ClientModel client = this.clientRepository.findById(clientId).orElseThrow(() -> null);
            Utils.copyNonNullProperties(clientDTO, client);
            this.clientRepository.save(client);
            return this.me(clientId);
        } catch (Exception error) {
            System.out.println("Error update user " + error.getMessage());
            return null;
        }
    }


    public Boolean delete(UUID id) {
        try {
            this.clientRepository.deleteById(id);
            return true;
        } catch (Exception error) {
            System.out.println("Error delete user " + error.getMessage());
            return false;
        }
    }

}

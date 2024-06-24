package br.com.agendaon.client;

import br.com.agendaon.user.UserModel;
import br.com.agendaon.user.UserService;
import br.com.agendaon.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    public ClientModel me(UUID userId) {
        return clientRepository.findByUserId(userId).orElse(null);
    }

    public ClientModel create(ClientDTO clientDTO, UUID userId) {
        try {
            UserModel user = this.userService.findById(userId);
            ClientModel clientModel = new ClientModel();
            clientModel.setDatabase(clientDTO);
            clientModel.setUser(user);
            return this.clientRepository.save(clientModel);
        } catch (Exception error) {
            System.out.println("Error create user " + error.getMessage());
            return null;
        }
    }

    public ClientModel update(ClientDTO clientDTO, UUID clientId) {
        try {
            ClientModel client = this.clientRepository.findById(clientId).orElseThrow(() -> null);
            Utils.copyNonNullProperties(clientDTO, client);
            return this.clientRepository.save(client);
        } catch (Exception error) {
            System.out.println("Error update user " + error.getMessage());
            return null;
        }
    }


    public Boolean delete(UUID id) {
        try {
            clientRepository.deleteById(id);
            return true;
        } catch (Exception error) {
            System.out.println("Error delete user " + error.getMessage());
            return false;
        }
    }

}

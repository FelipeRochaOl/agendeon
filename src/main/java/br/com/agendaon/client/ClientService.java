package br.com.agendaon.client;

import br.com.agendaon.user.UserModel;
import br.com.agendaon.user.UserService;
import br.com.agendaon.utils.Utils;
import br.com.agendaon.utils.ValidateCPF;
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
        ClientModel client = this.clientRepository.findByUserId(userId).orElseThrow(() -> null);
        return new ClientPresenter(client);
    }

    public ClientPresenter create(ClientDTO clientDTO) {
        String cpf = new ValidateCPF(clientDTO.getCpf()).isValidCPF().getCpf();
        clientDTO.setCpf(cpf);
        UserModel user = this.userService.findById(clientDTO.getUserId());
        ClientModel clientModel = new ClientModel(clientDTO);
        clientModel.setUser(user);
        ClientModel client = this.clientRepository.save(clientModel);
        return new ClientPresenter(client);
    }

    public ClientPresenter update(ClientDTO clientDTO) {
        ClientModel client = this.clientRepository.findById(clientDTO.getUserId()).orElseThrow(() -> null);
        Utils.copyNonNullProperties(clientDTO, client);
        this.clientRepository.save(client);
        return this.me(clientDTO.getUserId());
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

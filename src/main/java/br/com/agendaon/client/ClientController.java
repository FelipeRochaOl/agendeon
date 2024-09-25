package br.com.agendaon.client;

import br.com.agendaon.response.ResponsePresenter;
import br.com.agendaon.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<ResponsePresenter<List<ClientPresenter>>> findAll() {
        List<ClientPresenter> clients = this.clientService.findAll();
        ResponsePresenter<List<ClientPresenter>> response = new ResponsePresenter<>(true, clients);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponsePresenter<ClientPresenter>> me(HttpServletRequest request) {
        try {
            UserModel user = (UserModel) request.getAttribute("user");
            ClientPresenter me = this.clientService.me(user.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsePresenter<>(true, me));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<ClientPresenter>> create(@RequestBody ClientDTO clientDTO, HttpServletRequest request) {
        try {
            UserModel user = (UserModel) request.getAttribute("user");
            clientDTO.setUserId(user.getId());
            ClientPresenter client = this.clientService.create(clientDTO);
            ResponsePresenter<ClientPresenter> response = new ResponsePresenter<>(true, client);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponsePresenter<ClientPresenter>> update(@RequestBody ClientDTO clientDTO) {
        try {
            ClientPresenter client = this.clientService.update(clientDTO);
            ResponsePresenter<ClientPresenter> response = new ResponsePresenter<>(true, client);
            return ResponseEntity.ok(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePresenter<String>> delete(@PathVariable UUID id) {
        Boolean deleted = this.clientService.delete(id);
        if (!deleted) {
            ResponsePresenter<String> response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(new ResponsePresenter<>(true));
    }
}

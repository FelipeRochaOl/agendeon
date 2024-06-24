package br.com.agendaon.client;

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
    public ResponseEntity<List<ClientModel>> findAll() {
        List<ClientModel> clients = this.clientService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @GetMapping("/me")
    public ResponseEntity<ClientModel> me(HttpServletRequest request) {
        ClientModel me = this.clientService.me(this.getUserId(request));
        if (me == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(me);
    }

    @PostMapping("/")
    public ResponseEntity<ClientModel> create(@RequestBody ClientDTO clientDTO, HttpServletRequest request) {
        ClientModel client = this.clientService.create(clientDTO, this.getUserId(request));
        if (client == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientModel> update(@RequestBody ClientDTO clientDTO, @PathVariable UUID id) {
        ClientModel client = this.clientService.update(clientDTO, id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        Boolean isDeleted = clientService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private UUID getUserId(HttpServletRequest request) {
        return UUID.fromString(request.getAttribute("userId").toString());
    }
}

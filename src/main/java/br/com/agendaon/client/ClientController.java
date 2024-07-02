package br.com.agendaon.client;

import br.com.agendaon.response.ResponsePresenter;
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
    public ResponseEntity<List<ClientPresenter>> findAll() {
        List<ClientPresenter> clients = this.clientService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @GetMapping("/profile")
    public ResponseEntity<ResponsePresenter<ClientPresenter>> me(HttpServletRequest request) {
        try {
            ClientPresenter me = this.clientService.me(this.getUserId(request));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsePresenter<>(true, me));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<ClientPresenter>> create(@RequestBody ClientDTO clientDTO, HttpServletRequest request) {
        try {
            clientDTO.setUserId(this.getUserId(request));
            ClientPresenter client = this.clientService.create(clientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponsePresenter<>(true, client));
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePresenter<ClientPresenter>> update(@RequestBody ClientDTO clientDTO, @PathVariable UUID id) {
        try {
            clientDTO.setUserId(id);
            ClientPresenter client = this.clientService.update(clientDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponsePresenter<>(true, client));
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePresenter<String>> delete(@PathVariable UUID id) {
        Boolean isDeleted = this.clientService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponsePresenter<>(true));
    }

    private UUID getUserId(HttpServletRequest request) {
        return UUID.fromString(request.getAttribute("userId").toString());
    }
}

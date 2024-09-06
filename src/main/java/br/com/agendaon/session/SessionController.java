package br.com.agendaon.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @GetMapping("/")
    public ResponseEntity<List<SessionPresenter>> findAll() {
        List<SessionPresenter> sessions = this.sessionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sessions);
    }

    @PostMapping("/")
    public ResponseEntity<SessionPresenter> create(@RequestBody SessionDTO sessionDTO) {
        SessionPresenter session = this.sessionService.create(sessionDTO);
        if (session == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @PutMapping("/{code}")
    public ResponseEntity<SessionPresenter> update(@RequestBody SessionDTO sessionDTO, @PathVariable UUID code) {
        SessionPresenter session = this.sessionService.update(sessionDTO, code);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(session);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable UUID code) {
        Boolean isDeleted = this.sessionService.delete(code);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

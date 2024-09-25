package br.com.agendaon.session;

import br.com.agendaon.response.ResponsePresenter;
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
    public ResponseEntity<ResponsePresenter<List<SessionPresenter>>> findAll() {
        List<SessionPresenter> sessions = this.sessionService.findAll();
        ResponsePresenter<List<SessionPresenter>> response = new ResponsePresenter<>(true, sessions);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<SessionPresenter>> create(@RequestBody SessionDTO sessionDTO) {
        SessionPresenter session = this.sessionService.create(sessionDTO);
        if (session == null) {
            ResponsePresenter<SessionPresenter> response = new ResponsePresenter<>(false, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter<SessionPresenter> response = new ResponsePresenter<>(true, session);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ResponsePresenter<SessionPresenter>> update(@RequestBody SessionDTO sessionDTO, @PathVariable UUID code) {
        SessionPresenter session = this.sessionService.update(sessionDTO, code);
        if (session == null) {
            ResponsePresenter<SessionPresenter> response = new ResponsePresenter<>(false, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter<SessionPresenter> response = new ResponsePresenter<>(true, session);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ResponsePresenter> delete(@PathVariable UUID code) {
        Boolean isDeleted = this.sessionService.delete(code);
        if (!isDeleted) {
            ResponsePresenter response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter response = new ResponsePresenter<>(true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

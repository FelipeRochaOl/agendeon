package br.com.agendaon.service;

import br.com.agendaon.response.ResponsePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/")
    public ResponseEntity<ResponsePresenter<List<ServicePresenter>>> findAll() {
        List<ServicePresenter> services = this.serviceService.findAll();
        ResponsePresenter<List<ServicePresenter>> response = new ResponsePresenter<>(true, services);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<ServicePresenter>> create(@RequestBody ServiceDTO serviceDTO) {
        ServicePresenter service = this.serviceService.create(serviceDTO);
        if (service == null) {
            ResponsePresenter<ServicePresenter> response = new ResponsePresenter<>(false, null);
            return ResponseEntity.badRequest().body(response);
        }
        ResponsePresenter<ServicePresenter> response = new ResponsePresenter<>(true, service);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ResponsePresenter<ServicePresenter>> update(@RequestBody ServiceDTO serviceDTO, @PathVariable UUID code) {
        ServicePresenter service = this.serviceService.update(serviceDTO, code);
        if (service == null) {
            ResponsePresenter<ServicePresenter> response = new ResponsePresenter<>(false, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter<ServicePresenter> response = new ResponsePresenter<>(true, service);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ResponsePresenter> delete(@PathVariable UUID code) {
        Boolean isDeleted = this.serviceService.delete(code);
        if (!isDeleted) {
            ResponsePresenter<ServicePresenter> response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter<ServicePresenter> response = new ResponsePresenter<>(true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

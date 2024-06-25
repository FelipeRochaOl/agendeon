package br.com.agendaon.service;

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
    public ResponseEntity<List<ServicePresenter>> findAll() {
        List<ServicePresenter> services = this.serviceService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(services);
    }

    @PostMapping("/")
    public ResponseEntity<ServicePresenter> create(@RequestBody ServiceDTO serviceDTO) {
        ServicePresenter service = this.serviceService.create(serviceDTO);
        if (service == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ServicePresenter> update(@RequestBody ServiceDTO serviceDTO, @PathVariable UUID code) {
        ServicePresenter service = this.serviceService.update(serviceDTO, code);
        if (service == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(service);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable UUID code) {
        Boolean isDeleted = this.serviceService.delete(code);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

package br.com.agendaon.company;

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
@RequestMapping("/companies")

public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<ResponsePresenter<List<CompanyPresenter>>> findAll() {
        List<CompanyPresenter> companies = this.companyService.findAll();
        ResponsePresenter<List<CompanyPresenter>> response = new ResponsePresenter<>(true, companies);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<CompanyPresenter>> create(@RequestBody CompanyDTO companyDTO, HttpServletRequest request) {
        try {
            CompanyPresenter client = this.companyService.create(companyDTO);
            ResponsePresenter<CompanyPresenter> response = new ResponsePresenter<>(true, client);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PutMapping("/")
    public ResponseEntity<ResponsePresenter<CompanyPresenter>> update(@RequestBody CompanyDTO companyDTO) {
        try {
            CompanyPresenter client = this.companyService.update(companyDTO);
            ResponsePresenter<CompanyPresenter> response = new ResponsePresenter<>(true, client);
            return ResponseEntity.ok(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePresenter<String>> delete(@PathVariable UUID id) {
        Boolean deleted = this.companyService.delete(id);
        if (!deleted) {
            ResponsePresenter<String> response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(new ResponsePresenter<>(true));
    }
}

package br.com.agendaon.address;

import br.com.agendaon.response.ResponsePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/")
    public ResponseEntity<ResponsePresenter<List<AddressPresenter>>> findAll() {
        List<AddressPresenter> addresses = this.addressService.findAll();
        ResponsePresenter<List<AddressPresenter>> response = new ResponsePresenter<>(true, addresses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePresenter<AddressPresenter>> findById(@PathVariable UUID id) {
        try {
            AddressPresenter address = this.addressService.findById(id);
            if (address == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePresenter<>(false));
            }
            ResponsePresenter<AddressPresenter> response = new ResponsePresenter<>(true, address);
            return ResponseEntity.ok(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<AddressPresenter>> save(@RequestBody AddressDTO addressDTO) {
        try {
            addressDTO.validZip();
            AddressPresenter address = this.addressService.create(addressDTO);
            ResponsePresenter<AddressPresenter> response = new ResponsePresenter<>(true, address);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePresenter<AddressPresenter>> update(@RequestBody AddressDTO addressDTO, @PathVariable UUID id) {
        try {
            AddressPresenter address = this.addressService.update(addressDTO, id);
            ResponsePresenter<AddressPresenter> response = new ResponsePresenter<>(true, address);
            return ResponseEntity.ok(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePresenter<String>> delete(@PathVariable UUID id) {
        Boolean deleted = this.addressService.delete(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePresenter<>(false));
        }
        return ResponseEntity.ok(new ResponsePresenter<>(true));
    }
}

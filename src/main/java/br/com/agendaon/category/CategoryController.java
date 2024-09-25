package br.com.agendaon.category;

import br.com.agendaon.response.ResponsePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<ResponsePresenter<List<CategoryPresenter>>> findAll() {
        List<CategoryPresenter> categories = this.categoryService.findAll();
        ResponsePresenter<List<CategoryPresenter>> response = new ResponsePresenter<>(true, categories);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePresenter<CategoryPresenter>> create(@RequestBody CategoryDTO categoryDTO) {
        CategoryPresenter category = this.categoryService.create(categoryDTO);
        if (category == null) {
            ResponsePresenter<CategoryPresenter> response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter<CategoryPresenter> response = new ResponsePresenter<>(true, category);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ResponsePresenter<CategoryPresenter>> update(@RequestBody CategoryDTO categoryDTO, @PathVariable UUID code) {
        CategoryPresenter category = this.categoryService.update(categoryDTO, code);
        if (category == null) {
            ResponsePresenter<CategoryPresenter> response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter<CategoryPresenter> response = new ResponsePresenter<>(true, category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ResponsePresenter> delete(@PathVariable UUID code) {
        Boolean isDeleted = this.categoryService.delete(code);
        if (!isDeleted) {
            ResponsePresenter response = new ResponsePresenter<>(false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponsePresenter response = new ResponsePresenter<>(true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

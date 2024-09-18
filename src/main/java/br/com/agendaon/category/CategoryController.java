package br.com.agendaon.category;

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
    public ResponseEntity<List<CategoryPresenter>> findAll() {
        List<CategoryPresenter> categories = this.categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryPresenter> create(@RequestBody CategoryDTO categoryDTO) {
        CategoryPresenter category = this.categoryService.create(categoryDTO);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{code}")
    public ResponseEntity<CategoryPresenter> update(@RequestBody CategoryDTO categoryDTO, @PathVariable UUID code) {
        CategoryPresenter category = this.categoryService.update(categoryDTO, code);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> delete(@PathVariable UUID code) {
        Boolean isDeleted = this.categoryService.delete(code);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

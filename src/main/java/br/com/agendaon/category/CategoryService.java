package br.com.agendaon.category;

import br.com.agendaon.session.SessionModel;
import br.com.agendaon.session.SessionService;
import br.com.agendaon.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SessionService sessionService;

    public List<CategoryPresenter> findAll() {
        List<CategoryModel> categories = this.categoryRepository.findAll();
        List<CategoryPresenter> result = new ArrayList<>();
        categories.forEach(category -> result.add(new CategoryPresenter(category)));
        return result;
    }

    public CategoryPresenter findByCode(UUID code) {
        CategoryModel category = this.categoryRepository.findByCode(code).orElse(null);
        if (category == null) return null;
        return new CategoryPresenter(category);
    }

    public CategoryPresenter create(CategoryDTO categoryDTO) {
        try {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCode(categoryDTO.getCode());
            categoryModel.setName(categoryDTO.getName());
            SessionModel session = this.sessionService.findById(categoryDTO.getSessionCode());
            categoryModel.setSession(session);
            CategoryModel category = this.categoryRepository.save(categoryModel);
            return new CategoryPresenter(category);
        } catch (Exception error) {
            System.out.println("Error create category " + error.getMessage());
            return null;
        }
    }

    public CategoryPresenter update(CategoryDTO CategoryDTO, UUID code) {
        try {
            CategoryModel category = this.categoryRepository.findByCode(code).orElseThrow(() -> null);
            Utils.copyNonNullProperties(CategoryDTO, category);
            this.categoryRepository.save(category);
            return this.findByCode(code);
        } catch (Exception error) {
            System.out.println("Error update category " + error.getMessage());
            return null;
        }
    }

    public Boolean delete(UUID code) {
        try {
            CategoryModel category = this.categoryRepository.findByCode(code).orElseThrow(() -> null);
            this.categoryRepository.delete(category);
            return true;
        } catch (Exception error) {
            System.out.println("Error delete category " + error.getMessage());
            return false;
        }
    }
}

package br.com.agendaon.category;

import br.com.agendaon.session.SessionModel;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryPresenter {
    private UUID code;
    private SessionModel session;
    private String name;

    CategoryPresenter(CategoryModel categoryModel) {
        this.code = categoryModel.getCode();
        this.session = categoryModel.getSession();
        this.name = categoryModel.getName();
    }
}

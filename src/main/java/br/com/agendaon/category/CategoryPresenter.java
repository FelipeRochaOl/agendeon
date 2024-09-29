package br.com.agendaon.category;

import br.com.agendaon.session.SessionModel;
import br.com.agendaon.session.SessionPresenter;
import lombok.Data;

import java.text.Normalizer;
import java.util.UUID;

@Data
public class CategoryPresenter {
    private UUID code;
    private SessionPresenter session;
    private String name;
    private String urlPath;

    public CategoryPresenter(CategoryModel categoryModel) {
        this.code = categoryModel.getCode();
        this.name = categoryModel.getName();
        this.setSession(categoryModel.getSession());
        this.setUrlPath();
    }

    private void setUrlPath() {
        String name = this.removeAccents(this.name);
        this.urlPath = name.toLowerCase().replaceAll("[^0-9a-zA-Z]+", "-");
    }

    private String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private void setSession(SessionModel session) {
        this.session = new SessionPresenter(session);
    }
}

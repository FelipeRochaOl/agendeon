package br.com.agendaon.session;

import lombok.Data;

import java.text.Normalizer;
import java.util.UUID;

@Data
public class SessionPresenter {
    private UUID code;
    private String name;
    private String urlPath;

    public SessionPresenter(SessionModel sessionModel) {
        this.code = sessionModel.getCode();
        this.name = sessionModel.getName();
        this.setUrlPath();
    }

    private void setUrlPath() {
        String name = this.removeAccents(this.name);
        this.urlPath = name.toLowerCase().replaceAll("[^0-9a-zA-Z]+", "-");
    }

    private String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}

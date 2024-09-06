package br.com.agendaon.response;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ResponsePresenter<Response> {
    private Boolean success = false;
    private Response data = null;
    private String error;

    public ResponsePresenter(Boolean success) {
        this.success = success;
    }

    public ResponsePresenter(Boolean success, Response response) {
        this.success = success;
        this.data = response;
    }

    public ResponsePresenter(String error) {
        this.error = error;
    }
}

package br.com.agendaon.session;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionPresenter {
    private UUID code;
    private String name;

    SessionPresenter(SessionModel sessionModel) {
        this.code = sessionModel.getCode();
        this.name = sessionModel.getName();
    }
}

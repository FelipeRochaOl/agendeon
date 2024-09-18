package br.com.agendaon.session;

import br.com.agendaon.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public List<SessionPresenter> findAll() {
        List<SessionModel> sessions = this.sessionRepository.findAll();
        List<SessionPresenter> result = new ArrayList<>();
        sessions.forEach(session -> result.add(new SessionPresenter(session)));
        return result;
    }

    public SessionModel findById(UUID code) {
        return this.sessionRepository.findByCode(code).orElse(null);
    }

    public SessionPresenter findByCode(UUID code) {
        SessionModel session = this.sessionRepository.findByCode(code).orElse(null);
        if (session == null) return null;
        return new SessionPresenter(session);
    }

    public SessionPresenter create(SessionDTO sessionDTO) {
        try {
            SessionModel sessionModel = new SessionModel();
            sessionModel.setCode(sessionDTO.getCode());
            sessionModel.setName(sessionDTO.getName());
            SessionModel session = this.sessionRepository.save(sessionModel);
            return new SessionPresenter(session);
        } catch (Exception error) {
            System.out.println("Error create session " + error.getMessage());
            return null;
        }
    }

    public SessionPresenter update(SessionDTO sessionDTO, UUID code) {
        try {
            SessionModel session = this.sessionRepository.findByCode(code).orElseThrow(() -> null);
            Utils.copyNonNullProperties(sessionDTO, session);
            this.sessionRepository.save(session);
            return this.findByCode(code);
        } catch (Exception error) {
            System.out.println("Error update session " + error.getMessage());
            return null;
        }
    }

    public Boolean delete(UUID code) {
        try {
            SessionModel session = this.sessionRepository.findByCode(code).orElseThrow(() -> null);
            this.sessionRepository.delete(session);
            return true;
        } catch (Exception error) {
            System.out.println("Error delete session " + error.getMessage());
            return false;
        }
    }
}

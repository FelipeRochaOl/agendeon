package br.com.agendaon.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<SessionModel, UUID> {
    Optional<SessionModel> findByCode(UUID code);

    List<SessionModel> findByName(String name);
}

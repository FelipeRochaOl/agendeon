package br.com.agendaon.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, UUID> {
    Optional<ServiceModel> findByCode(UUID code);

    Optional<ServiceModel> deleteByCode(UUID code);
}

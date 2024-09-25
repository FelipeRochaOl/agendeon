package br.com.agendaon.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceModel, UUID> {
    Optional<ServiceModel> findByCode(UUID code);

    List<ServiceModel> findByCompanyId(UUID companyId);
}

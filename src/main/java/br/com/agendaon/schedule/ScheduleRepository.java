package br.com.agendaon.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleModel, UUID> {
    List<ScheduleModel> findByCompanyIdAndClientId(UUID companyId, UUID clientId);

    List<ScheduleModel> findByCompanyIdAndClientIdAndScheduleDateGreaterThanEqual(UUID companyId, UUID clientId, Date scheduleDate);
}

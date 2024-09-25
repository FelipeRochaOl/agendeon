package br.com.agendaon.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleModel, UUID> {
    List<ScheduleModel> findByCompanyIdAndClientId(UUID companyId, UUID clientId);

    List<ScheduleModel> findByCompanyId(UUID companyId);

    @Query(value = "SELECT * FROM \"AGENDEON\".\"T_AGON_SCHEDULES\" tas"
            + " WHERE tas.company_id = ?1"
            + " AND tas.client_id = ?2"
            + " AND tas.schedule_date >= ?3"
            , nativeQuery = true)
    List<ScheduleModel> findHistory(UUID companyId, UUID clientId, Date scheduleDate);
}
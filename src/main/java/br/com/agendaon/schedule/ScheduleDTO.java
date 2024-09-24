package br.com.agendaon.schedule;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class ScheduleDTO {
    private UUID id;
    private UUID companyId;
    private UUID clientId;
    private UUID serviceId;
    private double total;
    private Date scheduleDate;
    private int hour;
    private int minute;
    private StatusEnum status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

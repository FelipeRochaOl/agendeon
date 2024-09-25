package br.com.agendaon.schedule;

import br.com.agendaon.client.ClientModel;
import br.com.agendaon.company.CompanyModel;
import br.com.agendaon.service.ServiceModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_agon_schedules", schema = "AGENDEON")
public class ScheduleModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    private CompanyModel company;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    private ClientModel client;

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    private ServiceModel service;

    @Column(precision = 2)
    private double total;

    @Column()
    private Date scheduleDate;

    @Column()
    private int hour;

    @Column()
    private int minute;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ScheduleModel() {
    }

    public ScheduleModel(ScheduleDTO scheduleDTO) {
        this.setScheduleDate(scheduleDTO.getScheduleDate());
        this.setHour(scheduleDTO.getHour());
        this.setMinute(scheduleDTO.getMinute());
        this.setTotal(scheduleDTO.getTotal());
        this.setStatus(scheduleDTO.getStatus());
    }
}

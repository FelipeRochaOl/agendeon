package br.com.agendaon.schedule;

import br.com.agendaon.client.ClientPresenter;
import br.com.agendaon.company.CompanyPresenter;
import br.com.agendaon.service.ServicePresenter;
import br.com.agendaon.utils.DateFormatter;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class SchedulePresenter {
    private UUID id;
    private CompanyPresenter company;
    private ClientPresenter client;
    private ServicePresenter service;
    private double total;
    private String formattedTotal;
    private Date scheduleDate;
    private String formattedDate;
    private int hour;
    private int minute;
    private Enum<StatusEnum> status;

    public SchedulePresenter(ScheduleModel scheduleModel) {
        this.id = scheduleModel.getId();
        this.total = scheduleModel.getTotal();
        this.scheduleDate = scheduleModel.getScheduleDate();
        this.hour = scheduleModel.getHour();
        this.minute = scheduleModel.getMinute();
        this.status = scheduleModel.getStatus();
        this.setCompany(scheduleModel);
        this.setClient(scheduleModel);
        this.setService(scheduleModel);
        this.setFormattedTotal();
        this.setFormattedDate();
    }

    private void setCompany(ScheduleModel scheduleModel) {
        this.company = new CompanyPresenter(scheduleModel.getCompany());
    }

    private void setClient(ScheduleModel scheduleModel) {
        this.client = new ClientPresenter(scheduleModel.getClient());
    }

    private void setService(ScheduleModel scheduleModel) {
        this.service = new ServicePresenter(scheduleModel.getService());
    }

    private void setFormattedTotal() {
        this.formattedTotal = String.format("R$ %.2f", total);
    }

    private void setFormattedDate() {
        this.formattedDate = DateFormatter.formatterDateWithoutTime(this.scheduleDate);
    }
}

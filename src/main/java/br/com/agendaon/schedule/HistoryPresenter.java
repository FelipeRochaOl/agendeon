package br.com.agendaon.schedule;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryPresenter {
    private Date scheduleDate;
    private int hour;
    private int minute;

    HistoryPresenter(ScheduleModel schedule) {
        this.scheduleDate = schedule.getScheduleDate();
        this.hour = schedule.getHour();
        this.minute = schedule.getMinute();
    }
}

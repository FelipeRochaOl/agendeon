package br.com.agendaon.schedule;

import br.com.agendaon.client.ClientModel;
import br.com.agendaon.client.ClientService;
import br.com.agendaon.company.CompanyModel;
import br.com.agendaon.company.CompanyService;
import br.com.agendaon.service.ServiceModel;
import br.com.agendaon.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ServiceService serviceService;

    public SchedulePresenter create(ScheduleDTO scheduleDTO, UUID userId) throws Exception {
        ClientModel client = this.clientService.findByUserId(userId);
        if (client == null) throw new Exception("Client not found");
        CompanyModel company = this.companyService.findOne(scheduleDTO.getCompanyId());
        if (company == null) throw new Exception("Company not found");
        ServiceModel service = this.serviceService.findOne(scheduleDTO.getServiceId());
        if (service == null) throw new Exception("Service not found");
        ScheduleModel scheduleModel = new ScheduleModel(scheduleDTO);
        scheduleModel.setClient(client);
        scheduleModel.setCompany(company);
        scheduleModel.setService(service);
        ScheduleModel schedule = this.scheduleRepository.save(scheduleModel);
        return new SchedulePresenter(schedule);
    }

    public List<SchedulePresenter> findAll(UUID companyId, UUID userId) throws Exception {
        CompanyModel company = this.companyService.findOne(companyId);
        if (company == null) throw new Exception("Company not found");
        ClientModel client = this.clientService.findByUserId(userId);
        if (client == null) throw new Exception("Client not found");
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setClientId(client.getId());
        scheduleDTO.setCompanyId(company.getId());
        List<ScheduleModel> schedulesFilter = this.scheduleRepository.findByCompanyIdAndClientId(
                scheduleDTO.getCompanyId(),
                scheduleDTO.getClientId()
        );
        List<ScheduleModel> schedules = this.getValidSchedules(schedulesFilter);
        List<SchedulePresenter> schedulePresenters = new ArrayList<>();
        for (ScheduleModel schedule : schedules) {
            SchedulePresenter schedulePresenter = new SchedulePresenter(schedule);
            schedulePresenters.add(schedulePresenter);
        }
        return schedulePresenters;
    }

    public List<SchedulePresenter> findByCompany(UUID companyId) throws Exception {
        CompanyModel company = this.companyService.findOne(companyId);
        if (company == null) throw new Exception("Company not found");
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setCompanyId(company.getId());
        List<ScheduleModel> schedulesFilter = this.scheduleRepository.findByCompanyId(companyId);
        List<ScheduleModel> schedules = this.getValidSchedules(schedulesFilter);
        List<SchedulePresenter> schedulePresenters = new ArrayList<>();
        for (ScheduleModel schedule : schedules) {
            SchedulePresenter schedulePresenter = new SchedulePresenter(schedule);
            schedulePresenters.add(schedulePresenter);
        }
        return schedulePresenters;
    }

    private List<ScheduleModel> getValidSchedules(List<ScheduleModel> schedules) {
        List<ScheduleModel> validSchedules = new ArrayList<>();
        for (ScheduleModel schedule : schedules) {
            Date today = new Date();
            LocalDate date = LocalDate.ofInstant(today.toInstant(), ZoneId.systemDefault());
            LocalDate yesterday = date.minusDays(1);
            Date checkDate = Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (!schedule.getScheduleDate().after(checkDate)) continue;
            validSchedules.add(schedule);
        }
        return validSchedules;
    }

    public List<HistoryPresenter> findHistory(ScheduleDTO scheduleDTO, UUID userId) {
        ClientModel client = this.clientService.findByUserId(userId);
        if (client == null) return null;
        scheduleDTO.setClientId(client.getId());
        List<ScheduleModel> schedules = this.scheduleRepository.findHistory(
                scheduleDTO.getCompanyId(),
                scheduleDTO.getClientId(),
                scheduleDTO.getScheduleDate()
        );
        if (schedules.isEmpty()) return new ArrayList<>();
        List<HistoryPresenter> historyPresenters = new ArrayList<>();
        for (ScheduleModel schedule : schedules) {
            HistoryPresenter historyPresenter = new HistoryPresenter(schedule);
            historyPresenters.add(historyPresenter);
        }
        return historyPresenters;
    }
}

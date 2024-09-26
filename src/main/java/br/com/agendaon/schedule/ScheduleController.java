package br.com.agendaon.schedule;

import br.com.agendaon.client.ClientService;
import br.com.agendaon.response.ResponsePresenter;
import br.com.agendaon.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/{companyId}")
    public ResponseEntity<ResponsePresenter<List<SchedulePresenter>>> findAll(@PathVariable UUID companyId, HttpServletRequest request) {
        try {
            UserModel currentUser = (UserModel) request.getAttribute("user");
            List<SchedulePresenter> schedules = this.scheduleService.findAll(companyId, currentUser.getId());
            ResponsePresenter<List<SchedulePresenter>> response = new ResponsePresenter<>(true, schedules);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @GetMapping("/{companyId}/company")
    public ResponseEntity<ResponsePresenter<List<SchedulePresenter>>> findByCompany(@PathVariable UUID companyId) {
        try {
            List<SchedulePresenter> schedules = this.scheduleService.findByCompany(companyId);
            ResponsePresenter<List<SchedulePresenter>> response = new ResponsePresenter<>(true, schedules);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @GetMapping("/{companyId}/history")
    public ResponseEntity<ResponsePresenter<List<HistoryPresenter>>> findAllByCompanyId(@PathVariable UUID companyId, HttpServletRequest request) {
        try {
            UserModel currentUser = (UserModel) request.getAttribute("user");
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setCompanyId(companyId);
            scheduleDTO.setScheduleDate(new Date());
            List<HistoryPresenter> histories = this.scheduleService.findHistory(scheduleDTO, currentUser.getId());
            ResponsePresenter<List<HistoryPresenter>> response = new ResponsePresenter<>(true, histories);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PostMapping("/{companyId}/create")
    public ResponseEntity<ResponsePresenter<SchedulePresenter>> create(@RequestBody ScheduleDTO scheduleDTO, HttpServletRequest request) {
        try {
            UserModel currentUser = (UserModel) request.getAttribute("user");
            SchedulePresenter schedule = this.scheduleService.create(scheduleDTO, currentUser.getId());
            ResponsePresenter<SchedulePresenter> response = new ResponsePresenter<>(true, schedule);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }
}

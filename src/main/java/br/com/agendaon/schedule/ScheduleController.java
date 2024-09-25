package br.com.agendaon.schedule;

import br.com.agendaon.response.ResponsePresenter;
import br.com.agendaon.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/{companyId}")
    public ResponseEntity<ResponsePresenter<List<SchedulePresenter>>> findAll(@PathVariable UUID companyId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserModel currentUser = (UserModel) authentication.getPrincipal();
            List<SchedulePresenter> schedules = this.scheduleService.findAll(companyId, currentUser.getId());
            ResponsePresenter<List<SchedulePresenter>> response = new ResponsePresenter<>(true, schedules);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @GetMapping("/{companyId}/history")
    public ResponseEntity<ResponsePresenter<List<HistoryPresenter>>> findAllByCompanyId(@PathVariable UUID companyId) {
        try {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setCompanyId(companyId);
            scheduleDTO.setScheduleDate(new Date());
            List<HistoryPresenter> histories = this.scheduleService.findHistory(scheduleDTO);
            ResponsePresenter<List<HistoryPresenter>> response = new ResponsePresenter<>(true, histories);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.badRequest().body(new ResponsePresenter<>(error.getMessage()));
        }
    }

    @PostMapping("/{companyId}/create")
    public ResponseEntity<ResponsePresenter<SchedulePresenter>> create(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            SchedulePresenter schedule = this.scheduleService.create(scheduleDTO);
            ResponsePresenter<SchedulePresenter> response = new ResponsePresenter<>(true, schedule);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

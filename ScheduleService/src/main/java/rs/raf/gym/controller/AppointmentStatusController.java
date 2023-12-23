package rs.raf.gym.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.dto.appointment_status.AppointmentStatusCreateDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusUpdateDto;
import rs.raf.gym.service.IAppointmentStatusService;

@AllArgsConstructor
@RestController
@RequestMapping("/appointment-status")
public class AppointmentStatusController {

    private final IAppointmentStatusService service;

    @GetMapping
    public ResponseEntity<Page<AppointmentStatusDto>> filter(@RequestParam(value = "name", required = false) String name,
                                                             Pageable pageable) {
        return new ResponseEntity<>(service.findAll(name, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppointmentStatusDto> create(@RequestBody @Valid AppointmentStatusCreateDto createDto) {
        return new ResponseEntity<>(service.create(createDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AppointmentStatusDto> update(@RequestBody @Valid AppointmentStatusUpdateDto updateDto) {
        return new ResponseEntity<>(service.update(updateDto), HttpStatus.OK);
    }

}

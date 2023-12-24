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
import rs.raf.gym.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.service.ITrainingAppointmentService;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@RestController
@RequestMapping("/training-appointment")
public class TrainingAppointmentController {

    private final ITrainingAppointmentService service;

    @GetMapping
    public ResponseEntity<Page<TrainingAppointmentDto>> filter(@RequestParam(value = "gym",      required = false) String    gym,
                                                               @RequestParam(value = "training", required = false) String    training,
                                                               @RequestParam(value = "date",     required = false) LocalDate date,
                                                               @RequestParam(value = "time",     required = false) LocalTime time,
                                                               @RequestParam(value = "duration", required = false) Integer   duration,
                                                               @RequestParam(value = "status",   required = false) String    status,
                                                               Pageable pageable) {
        return new ResponseEntity<>(service.findAll(gym, training, date, time, duration, status, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrainingAppointmentDto> create(@RequestBody @Valid TrainingAppointmentCreateDto createDto) {
        return new ResponseEntity<>(service.create(createDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TrainingAppointmentDto> update(@RequestBody @Valid TrainingAppointmentUpdateDto updateDto) {
        return new ResponseEntity<>(service.update(updateDto), HttpStatus.OK);
    }

}

package rs.raf.gym.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentUpdateDto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ITrainingAppointmentService {

    Page<TrainingAppointmentDto> findAll(String gym, String training, LocalDate date, LocalTime time, Integer duration,
                                         String status, Pageable pageable);

    TrainingAppointmentDto create(TrainingAppointmentCreateDto createDto);

    TrainingAppointmentDto update(TrainingAppointmentUpdateDto updateDto);

}

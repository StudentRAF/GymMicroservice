package rs.raf.gym.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.gym.dto.appointment_status.AppointmentStatusCreateDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusUpdateDto;

public interface IAppointmentStatusService {

    Page<AppointmentStatusDto> findAll(String name, Pageable pageable);

    AppointmentStatusDto create(AppointmentStatusCreateDto createDto);

    AppointmentStatusDto update(AppointmentStatusUpdateDto updateDto);

}

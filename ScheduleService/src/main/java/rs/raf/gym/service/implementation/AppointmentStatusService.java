package rs.raf.gym.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.raf.gym.dto.appointment_status.AppointmentStatusCreateDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusUpdateDto;
import rs.raf.gym.mapper.AppointmentStatusMapper;
import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.repository.IAppointmentStatusRepository;
import rs.raf.gym.service.IAppointmentStatusService;
import rs.raf.gym.specification.AppointmentStatusSpecification;

@Service
@AllArgsConstructor
public class AppointmentStatusService implements IAppointmentStatusService {

    private final IAppointmentStatusRepository repository;
    private final AppointmentStatusMapper      mapper;

    @Override
    public Page<AppointmentStatusDto> findAll(String name, Pageable pageable) {
        AppointmentStatusSpecification specification = new AppointmentStatusSpecification(name);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toAppointmentStatusDto);
    }

    @Override
    public AppointmentStatusDto create(AppointmentStatusCreateDto createDto) {
        AppointmentStatus appointmentStatus = new AppointmentStatus();

        mapper.map(appointmentStatus, createDto);

        return mapper.toAppointmentStatusDto(repository.save(appointmentStatus));
    }

    @Override
    public AppointmentStatusDto update(AppointmentStatusUpdateDto updateDto) {
        AppointmentStatus appointmentStatus = repository.findById(updateDto.getOldName())
                                                        .orElse(null);

        if (appointmentStatus == null) //TODO: Replace with exception
            return null;

        if (appointmentStatus.getName().equals(updateDto.getName()))
            return mapper.toAppointmentStatusDto(appointmentStatus);

        repository.delete(appointmentStatus);

        mapper.map(appointmentStatus, updateDto);

        return mapper.toAppointmentStatusDto(repository.save(appointmentStatus));
    }

}

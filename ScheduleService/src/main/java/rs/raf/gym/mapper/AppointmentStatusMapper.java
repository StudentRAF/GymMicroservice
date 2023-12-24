package rs.raf.gym.mapper;

import org.springframework.stereotype.Component;
import rs.raf.gym.dto.appointment_status.AppointmentStatusCreateDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusDto;
import rs.raf.gym.dto.appointment_status.AppointmentStatusUpdateDto;
import rs.raf.gym.model.AppointmentStatus;

@Component
public class AppointmentStatusMapper {

    /**
     * Maps AppointmentStatus to AppointmentStatusDto object.
     * @param appointmentStatus appointment status
     * @return AppointmentStatusDto object
     */
    public AppointmentStatusDto toAppointmentStatusDto(AppointmentStatus appointmentStatus) {
        return new AppointmentStatusDto(appointmentStatus.getName());
    }

    /**
     * Maps AppointmentStatusCreateDto to existing AppointmentStatus object.
     * @param appointmentStatus appointment status
     * @param createDto create dto
     * @return AppointmentStatus object
     */
    public AppointmentStatus map(AppointmentStatus appointmentStatus, AppointmentStatusCreateDto createDto) {
        appointmentStatus.setName(createDto.getName());

        return appointmentStatus;
    }

    /**
     * Maps AppointmentStatusUpdateDto to existing AppointmentStatus object.
     * @param appointmentStatus appointment status
     * @param updateDto update dto
     * @return AppointmentStatus object
     */
    public AppointmentStatus map(AppointmentStatus appointmentStatus, AppointmentStatusUpdateDto updateDto) {
        appointmentStatus.setName(updateDto.getName());

        return appointmentStatus;
    }

}

package rs.raf.gym.dto.training_appointment;

import lombok.Getter;
import lombok.Setter;
import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.Training;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class TrainingAppointmentDto {

    private Gym gym;

    private Training training;

    private LocalDate date;

    private LocalTime time;

    private Integer duration;

    private AppointmentStatus status;

}

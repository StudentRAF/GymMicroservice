package rs.raf.gym.dto.training_appointment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class TrainingAppointmentUpdateDto {

    @NotNull
    private String gymName;

    @NotNull
    private String trainingName;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    private String statusName;


}

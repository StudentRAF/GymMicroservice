package rs.raf.gym.dto.training_appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class TrainingAppointmentCreateDto {

    @NotNull
    private String gymName;

    @NotNull
    private String trainingName;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    @Positive
    private Integer duration;

    @NotBlank
    private String statusName;

}

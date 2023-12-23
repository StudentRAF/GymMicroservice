package rs.raf.gym.dto.gym_training;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GymTrainingCreateDto {

    @NotNull
    private String gymName;

    @NotNull
    private String trainingName;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Integer maxParticipants;

    @NotNull
    @Positive
    private Integer minParticipants;

}

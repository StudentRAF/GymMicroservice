package rs.raf.gym.dto.gym_training;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GymTrainingUpdateDto {

    @NotNull
    @Size(max = 40)
    private String gymName;

    @NotNull
    @Size(max = 40)
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

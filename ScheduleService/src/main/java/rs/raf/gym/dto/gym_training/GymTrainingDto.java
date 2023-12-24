package rs.raf.gym.dto.gym_training;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.Training;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GymTrainingDto {

    private Gym gym;

    private Training training;

    private Double price;

    private Integer maxParticipants;

    private Integer minParticipants;

}

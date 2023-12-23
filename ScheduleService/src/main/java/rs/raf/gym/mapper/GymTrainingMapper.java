package rs.raf.gym.mapper;

import org.springframework.stereotype.Component;
import rs.raf.gym.dto.gym_training.GymTrainingCreateDto;
import rs.raf.gym.dto.gym_training.GymTrainingDto;
import rs.raf.gym.dto.gym_training.GymTrainingUpdateDto;
import rs.raf.gym.model.GymTraining;

@Component
public class GymTrainingMapper {

    /**
     * Maps GymTraining to GymTrainingDto object.
     * @param gymTraining gym training
     * @return GymTrainingDto object
     */
    public GymTrainingDto mapGymTrainingDto(GymTraining gymTraining) {
        GymTrainingDto gymTrainingDto = new GymTrainingDto();

        gymTrainingDto.setGym(gymTraining.getGymTraining().getGym());
        gymTrainingDto.setTraining(gymTraining.getGymTraining().getTraining());
        gymTrainingDto.setPrice(gymTraining.getPrice());
        gymTrainingDto.setMinParticipants(gymTraining.getMinParticipants());
        gymTrainingDto.setMaxParticipants(gymTraining.getMaxParticipants());

        return gymTrainingDto;
    }

    /**
     * Maps GymTrainingCreateDto to existing GymTraining object.
     * @param gymTraining gymTraining
     * @param createDto create dto
     * @return GymTraining object
     */
    public GymTraining map(GymTraining gymTraining, GymTrainingCreateDto createDto) {
        gymTraining.setPrice(createDto.getPrice());
        gymTraining.setMinParticipants(createDto.getMinParticipants());
        gymTraining.setMaxParticipants(createDto.getMaxParticipants());

        return gymTraining;
    }

    /**
     * Maps GymTrainingUpdateDto to existing GymTraining object.
     * @param gymTraining gym training
     * @param updateDto update dto
     * @return GymTraining object
     */
    public GymTraining map(GymTraining gymTraining, GymTrainingUpdateDto updateDto) {
        gymTraining.setPrice(updateDto.getPrice());
        gymTraining.setMinParticipants(updateDto.getMinParticipants());
        gymTraining.setMaxParticipants(updateDto.getMaxParticipants());

        return gymTraining;
    }

}

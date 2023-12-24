package rs.raf.gym.mapper;

import org.springframework.stereotype.Component;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.model.TrainingAppointment;

@Component
public class TrainingAppointmentMapper {

    /**
     * Maps TrainingAppointment to TrainingAppointmentDto object.
     * @param trainingAppointment training appointment
     * @return TrainingAppointmentDto object
     */
    public TrainingAppointmentDto toTrainingAppointmentDto(TrainingAppointment trainingAppointment) {
        TrainingAppointmentDto trainingAppointmentDto = new TrainingAppointmentDto();

        trainingAppointmentDto.setGym(trainingAppointment.getTrainingAppointment()
                                                         .getGymTraining()
                                                         .getGym());
        trainingAppointmentDto.setTraining(trainingAppointment.getTrainingAppointment()
                                                              .getGymTraining()
                                                              .getTraining());
        trainingAppointmentDto.setDate(trainingAppointment.getTrainingAppointment()
                                                          .getDate());
        trainingAppointmentDto.setTime(trainingAppointment.getTrainingAppointment()
                                                          .getTime());
        trainingAppointmentDto.setDuration(trainingAppointment.getDuration());
        trainingAppointmentDto.setStatus(trainingAppointment.getStatus());

        return trainingAppointmentDto;
    }

    /**
     * Maps TrainingAppointmentCreateDto to existing TrainingAppointment object.
     * @param trainingAppointment training appointment
     * @param createDto create dto
     * @return TrainingAppointment object
     */
    public TrainingAppointment map(TrainingAppointment trainingAppointment, TrainingAppointmentCreateDto createDto) {
        trainingAppointment.setDuration(createDto.getDuration());

        return trainingAppointment;
    }

    /**
     * Maps TrainingAppointmentUpdateDto to existing TrainingAppointment object.
     * @param trainingAppointment training appointment
     * @param updateDto update dto
     * @return TrainingAppointment object
     */
    public TrainingAppointment map(TrainingAppointment trainingAppointment, TrainingAppointmentUpdateDto updateDto) {
        return trainingAppointment;
    }

}

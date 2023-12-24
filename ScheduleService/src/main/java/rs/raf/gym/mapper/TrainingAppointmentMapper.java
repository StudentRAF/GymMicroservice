/*
 * Copyright (C) 2023. Lazar Dobrota and Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.raf.gym.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.model.TrainingAppointment;

@Component
@AllArgsConstructor
public class TrainingAppointmentMapper {

    private final GymMapper               gymMapper;
    private final TrainingMapper          trainingMapper;
    private final AppointmentStatusMapper appointmentStatusMapper;

    /**
     * Maps TrainingAppointment to TrainingAppointmentDto object.
     * @param trainingAppointment training appointment
     * @return TrainingAppointmentDto object
     */
    public TrainingAppointmentDto toTrainingAppointmentDto(TrainingAppointment trainingAppointment) {
        return new TrainingAppointmentDto(gymMapper.toGymDto(trainingAppointment.getGymTraining().getGym()),
                                          trainingMapper.toTrainingDto(trainingAppointment.getGymTraining().getTraining()),
                                          trainingAppointment.getDate(),
                                          trainingAppointment.getTime(),
                                          trainingAppointment.getDuration(),
                                          appointmentStatusMapper.toAppointmentStatusDto(trainingAppointment.getStatus()));
    }

    /**
     * Maps TrainingAppointmentCreateDto to existing TrainingAppointment object.
     * @param trainingAppointment training appointment
     * @param createDto create dto
     * @return TrainingAppointment object
     */
    public TrainingAppointment map(TrainingAppointment trainingAppointment, TrainingAppointmentCreateDto createDto) {
        trainingAppointment.setDate(createDto.getDate());
        trainingAppointment.setTime(createDto.getTime());
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
        trainingAppointment.setDuration(updateDto.getDuration());

        return trainingAppointment;
    }

}

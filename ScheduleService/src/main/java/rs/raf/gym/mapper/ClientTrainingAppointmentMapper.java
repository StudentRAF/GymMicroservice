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
import rs.raf.gym.dto.client_training_appointment.ClientTrainingAppointmentCreateDto;
import rs.raf.gym.dto.client_training_appointment.ClientTrainingAppointmentDto;
import rs.raf.gym.dto.client_training_appointment.ClientTrainingAppointmentUpdateDto;
import rs.raf.gym.model.ClientTrainingAppointment;

@Component
@AllArgsConstructor
public class ClientTrainingAppointmentMapper {

    private final TrainingAppointmentMapper     trainingAppointmentMapper;
    private final ClientAppointmentStatusMapper clientAppointmentStatusMapper;

    /**
     * Maps ClientTrainingAppointment to ClientTrainingAppointmentDto object.
     * @param trainingAppointment training appointment
     * @return ClientTrainingAppointmentDto object
     */
    public ClientTrainingAppointmentDto toClientTrainingAppointmentDto(ClientTrainingAppointment trainingAppointment) {
        return new ClientTrainingAppointmentDto(trainingAppointmentMapper.toTrainingAppointmentDto(trainingAppointment.getTrainingAppointment()),
                                                trainingAppointment.getClientId(),
                                                clientAppointmentStatusMapper.toAppointmentStatusDto(trainingAppointment.getStatus()));
    }

    /**
     * Maps ClientTrainingAppointmentCreateDto to existing ClientTrainingAppointment object.
     * @param trainingAppointment training appointment
     * @param createDto create dto
     * @return ClientTrainingAppointment object
     */
    public ClientTrainingAppointment map(ClientTrainingAppointment trainingAppointment,
                                         ClientTrainingAppointmentCreateDto createDto) {
        trainingAppointment.setClientId(createDto.getClientId());

        return trainingAppointment;
    }

    /**
     * Maps ClientTrainingAppointmentUpdateDto to existing ClientTrainingAppointment object.
     * @param trainingAppointment training appointment
     * @param updateDto update dto
     * @return ClientTrainingAppointment object
     */
    public ClientTrainingAppointment map(ClientTrainingAppointment trainingAppointment,
                                         ClientTrainingAppointmentUpdateDto updateDto) {
        return trainingAppointment;
    }

}

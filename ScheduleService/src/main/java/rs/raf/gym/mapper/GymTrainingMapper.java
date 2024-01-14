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
import rs.raf.gym.commons.dto.gym_training.GymTrainingCreateDto;
import rs.raf.gym.commons.dto.gym_training.GymTrainingDto;
import rs.raf.gym.commons.dto.gym_training.GymTrainingUpdateDto;
import rs.raf.gym.model.GymTraining;

@Component
@AllArgsConstructor
public class GymTrainingMapper {

    private final GymMapper      gymMapper;
    private final TrainingMapper trainingMapper;

    /**
     * Maps GymTraining to GymTrainingDto object.
     * @param gymTraining gym training
     * @return GymTrainingDto object
     */
    public GymTrainingDto toGymTrainingDto(GymTraining gymTraining) {
        return new GymTrainingDto(gymMapper.toGymDto(gymTraining.getGym()),
                                  trainingMapper.toTrainingDto(gymTraining.getTraining()),
                                  gymTraining.getPrice(),
                                  gymTraining.getMaxParticipants(),
                                  gymTraining.getMinParticipants());
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

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

import org.springframework.stereotype.Component;
import rs.raf.gym.commons.dto.training_type.TrainingTypeCreateDto;
import rs.raf.gym.commons.dto.training_type.TrainingTypeDto;
import rs.raf.gym.commons.dto.training_type.TrainingTypeUpdateDto;
import rs.raf.gym.model.TrainingType;

@Component
public class TrainingTypeMapper {

    /**
     * Maps TrainingType to TrainingTypeDto object.
     * @param trainingType training type
     * @return TrainingTypeDto object
     */
    public TrainingTypeDto toTrainingTypeDto(TrainingType trainingType) {
        return new TrainingTypeDto(trainingType.getName());
    }

    /**
     * Maps TrainingTypeCreateDto to existing TrainingType object.
     * @param trainingType training type
     * @param createDto create dto
     * @return TrainingType object
     */
    public TrainingType map(TrainingType trainingType, TrainingTypeCreateDto createDto) {
        trainingType.setName(createDto.getName());

        return trainingType;
    }

    /**
     * Maps TrainingTypeUpdateDto to existing TrainingType object.
     * @param trainingType training type
     * @param updateDto update dto
     * @return TrainingType object
     */
    public TrainingType map(TrainingType trainingType, TrainingTypeUpdateDto updateDto) {
        trainingType.setName(updateDto.getName());

        return trainingType;
    }

}

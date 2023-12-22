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
import rs.raf.gym.dto.training.TrainingCreateDto;
import rs.raf.gym.dto.training.TrainingDto;
import rs.raf.gym.dto.training.TrainingUpdateDto;
import rs.raf.gym.model.Training;

@Component
public class TrainingMapper {

    public TrainingDto toTrainingDto(Training training) {
        TrainingDto trainingDto = new TrainingDto();

        trainingDto.setName(training.getName());
        trainingDto.setType(training.getType());
        trainingDto.setDescription(training.getDescription());
        trainingDto.setLoyalty(training.getLoyalty());

        return trainingDto;
    }

    public Training toTraining(TrainingCreateDto trainingCreateDto) {
        Training training = new Training();

        training.setName(trainingCreateDto.getName());
        training.setType(trainingCreateDto.getType());
        training.setDescription(trainingCreateDto.getDescription());
        training.setLoyalty(trainingCreateDto.getLoyalty());

        return training;
    }

    public Training toTraining(TrainingUpdateDto trainingUpdateDto) {
        Training training = new Training();

        training.setName(trainingUpdateDto.getName());
        training.setType(trainingUpdateDto.getType());
        training.setDescription(trainingUpdateDto.getDescription());
        training.setLoyalty(trainingUpdateDto.getLoyalty());

        return training;
    }

}

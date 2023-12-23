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

package rs.raf.gym.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rs.raf.gym.dto.training.TrainingCreateDto;
import rs.raf.gym.dto.training.TrainingDto;
import rs.raf.gym.dto.training.TrainingUpdateDto;
import rs.raf.gym.mapper.TrainingMapper;
import rs.raf.gym.model.Training;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.ITrainingService;
import rs.raf.gym.specification.TrainingSpecification;

@Service
public class TrainingService implements ITrainingService {

    private final ITrainingRepository trainingRepository;
    private final TrainingMapper      trainingMapper;

    public TrainingService(ITrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper     = trainingMapper;
    }

    @Override
    public Page<TrainingDto> findAll(String name, String type, Integer loyalty, Pageable pageable) {
        Specification<Training> specification = TrainingSpecification.of(name, type, loyalty);

        return trainingRepository.findAll(specification, pageable)
                                 .map(trainingMapper::toTrainingDto);
    }

    @Override
    public TrainingDto create(TrainingCreateDto trainingCreateDto) {
        Training training = trainingMapper.toTraining(trainingCreateDto);

        return trainingMapper.toTrainingDto(trainingRepository.save(training));
    }

    @Override
    public TrainingDto update(TrainingUpdateDto trainingUpdateDto) {
        Training training = trainingRepository.findById(trainingUpdateDto.getOldName())
                                              .orElse(null);

        if (training == null)
            return null;

        if (!training.getName().equals(trainingUpdateDto.getName()))
            trainingRepository.delete(training);

        training = trainingMapper.toTraining(trainingUpdateDto);

        return trainingMapper.toTrainingDto(trainingRepository.save(training));
    }

}

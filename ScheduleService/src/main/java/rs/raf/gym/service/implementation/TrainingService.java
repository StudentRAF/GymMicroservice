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

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.raf.gym.dto.training.TrainingCreateDto;
import rs.raf.gym.dto.training.TrainingDto;
import rs.raf.gym.dto.training.TrainingUpdateDto;
import rs.raf.gym.mapper.TrainingMapper;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingType;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.repository.ITrainingTypeRepository;
import rs.raf.gym.service.ITrainingService;
import rs.raf.gym.specification.TrainingSpecification;

@Service
@AllArgsConstructor
public class TrainingService implements ITrainingService {

    private final ITrainingRepository     repository;
    private final ITrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper          mapper;

    @Override
    public Page<TrainingDto> findAll(String name, String type, Integer loyalty, Pageable pageable) {
        TrainingSpecification specification = new TrainingSpecification(name, type, loyalty);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::mapTrainingDto);
    }

    @Override
    public TrainingDto create(TrainingCreateDto trainingCreateDto) {
        TrainingType trainingType = trainingTypeRepository.findById(trainingCreateDto.getType())
                                                          .orElse(null);

        if (trainingType == null) //TODO: Replace with exception
            return null;

        Training training = new Training();

        training.setType(trainingType);
        mapper.map(training, trainingCreateDto);

        return mapper.mapTrainingDto(repository.save(training));
    }

    @Override
    public TrainingDto update(TrainingUpdateDto trainingUpdateDto) {
        Training training = repository.findById(trainingUpdateDto.getOldName())
                                      .orElse(null);

        if (training == null) //TODO: Replace with exception
            return null;

        if (!training.getName().equals(trainingUpdateDto.getName()))
            repository.delete(training);

        mapper.map(training, trainingUpdateDto);

        return mapper.mapTrainingDto(repository.save(training));
    }

}

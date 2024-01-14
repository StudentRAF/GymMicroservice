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
import rs.raf.gym.commons.dto.training.TrainingCreateDto;
import rs.raf.gym.commons.dto.training.TrainingDto;
import rs.raf.gym.commons.dto.training.TrainingUpdateDto;
import rs.raf.gym.commons.exception.GymException;
import rs.raf.gym.exception.ExceptionType;
import rs.raf.gym.mapper.TrainingMapper;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingType;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.repository.ITrainingTypeRepository;
import rs.raf.gym.service.ITrainingService;
import rs.raf.gym.specification.TrainingSpecification;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainingService implements ITrainingService {

    private final ITrainingRepository     repository;
    private final ITrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper          mapper;

    @Override
    public List<TrainingDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toTrainingDto)
                         .toList();
    }

    @Override
    public Page<TrainingDto> findAll(String name, String type, Integer loyalty, Pageable pageable) {
        TrainingSpecification specification = new TrainingSpecification(name, type, loyalty);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toTrainingDto);
    }

    @Override
    public TrainingDto create(TrainingCreateDto createDto) throws GymException {
        TrainingType trainingType = trainingTypeRepository.findByName(createDto.getType())
                                                          .orElseThrow(() -> new GymException(ExceptionType.CREATE_TRAINING_NOT_FOUND_TRAINING_TYPE,
                                                                                              createDto.getType()));

        Training training = new Training();

        training.setType(trainingType);
        mapper.map(training, createDto);

        return mapper.toTrainingDto(repository.save(training));
    }

    @Override
    public TrainingDto update(TrainingUpdateDto updateDto) throws GymException {
        Training training = repository.findByName(updateDto.getOldName())
                                      .orElseThrow(() -> new GymException(ExceptionType.UPDATE_TRAINING_NOT_FOUND_TRAINING,updateDto.getOldName()));

        mapper.map(training, updateDto);

        return mapper.toTrainingDto(repository.save(training));
    }

}

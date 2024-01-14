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
import rs.raf.gym.commons.dto.gym_training.GymTrainingCreateDto;
import rs.raf.gym.commons.dto.gym_training.GymTrainingDto;
import rs.raf.gym.commons.dto.gym_training.GymTrainingUpdateDto;
import rs.raf.gym.commons.exception.GymException;
import rs.raf.gym.exception.ExceptionType;
import rs.raf.gym.mapper.GymTrainingMapper;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.repository.IGymTrainingRepository;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.IGymTrainingService;
import rs.raf.gym.specification.GymTrainingSpecification;

@Service
@AllArgsConstructor
public class GymTrainingService implements IGymTrainingService {

    private final IGymTrainingRepository repository;
    private final IGymRepository         gymRepository;
    private final ITrainingRepository    trainingRepository;
    private final GymTrainingMapper      mapper;

    @Override
    public Page<GymTrainingDto> findAll(String gym, String training, Double price, Integer minParticipants,
                                        Integer maxParticipants, Pageable pageable) {
        GymTrainingSpecification specification = new GymTrainingSpecification(gym, training, price, minParticipants,
                                                                              maxParticipants);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toGymTrainingDto);
    }

    @Override
    public GymTrainingDto create(GymTrainingCreateDto createDto) throws GymException {
        Gym gym = gymRepository.findByName(createDto.getGymName())
                               .orElseThrow(() -> new GymException(ExceptionType.CREATE_GYM_TRAINING_NOT_FOUND_GYM, createDto.getGymName()));

        Training training = trainingRepository.findByName(createDto.getTrainingName())
                                              .orElseThrow(() -> new GymException(ExceptionType.CREATE_GYM_TRAINING_NOT_FOUND_TRAINING, createDto.getTrainingName()));

        GymTraining gymTraining = new GymTraining();

        gymTraining.setGym(gym);
        gymTraining.setTraining(training);
        mapper.map(gymTraining, createDto);

        return mapper.toGymTrainingDto(repository.save(gymTraining));
    }

    @Override
    public GymTrainingDto update(GymTrainingUpdateDto updateDto) throws GymException {
        Gym gym = gymRepository.findByName(updateDto.getGymName())
                               .orElseThrow(() -> new GymException(ExceptionType.UPDATE_GYM_TRAINING_NOT_FOUND_GYM, updateDto.getGymName()));

        Training training = trainingRepository.findByName(updateDto.getTrainingName())
                                              .orElseThrow(() -> new GymException(ExceptionType.UPDATE_GYM_TRAINING_NOT_FOUND_TRAINING, updateDto.getTrainingName()));

        GymTraining gymTraining = repository.findByGymAndTraining(gym, training)
                                            .orElseThrow(() -> new GymException(ExceptionType.UPDATE_GYM_TRAINING_NOT_FOUND_GYM_TRAINING,
                                                                                updateDto.getGymName(), updateDto.getTrainingName()));

        mapper.map(gymTraining, updateDto);

        return mapper.toGymTrainingDto(repository.save(gymTraining));
    }

}

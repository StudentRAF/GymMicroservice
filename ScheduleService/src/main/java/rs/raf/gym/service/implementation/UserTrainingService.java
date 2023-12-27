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
import rs.raf.gym.commons.dto.user_training.UserTrainingCreateDto;
import rs.raf.gym.commons.dto.user_training.UserTrainingDto;
import rs.raf.gym.commons.dto.user_training.UserTrainingUpdateDto;
import rs.raf.gym.mapper.UserTrainingMapper;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.UserTraining;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.repository.IUserTrainingRepository;
import rs.raf.gym.service.IUserTrainingService;
import rs.raf.gym.specification.UserTrainingSpecification;

@Service
@AllArgsConstructor
public class UserTrainingService implements IUserTrainingService {

    private final IUserTrainingRepository repository;
    private final ITrainingRepository     trainingRepository;
    private final UserTrainingMapper      mapper;

    @Override
    public Page<UserTrainingDto> findAll(String training, Long clientId, Pageable pageable) {
        UserTrainingSpecification specification = new UserTrainingSpecification(training, clientId);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toUserTrainingDto);
    }

    @Override
    public UserTrainingDto create(UserTrainingCreateDto createDto) {
        UserTraining userTraining = new UserTraining();

        Training training = trainingRepository.findByName(createDto.getTrainingName())
                                              .orElse(null);

        if (training == null)
            return null;

        userTraining.setTraining(training);
        mapper.map(userTraining, createDto);

        return mapper.toUserTrainingDto(repository.save(userTraining));
    }

    @Override
    public UserTrainingDto update(UserTrainingUpdateDto updateDto) {
        Training training = trainingRepository.findByName(updateDto.getTrainingName())
                                              .orElse(null);

        if (training == null)
            return null;

        UserTraining userTraining = repository.findByTrainingAndClientId(training, updateDto.getClientId())
                                              .orElse(null);

        if (userTraining == null)
            return null;

        mapper.map(userTraining, updateDto);

        return mapper.toUserTrainingDto(repository.save(userTraining));
    }

}

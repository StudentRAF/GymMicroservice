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
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import rs.raf.gym.commons.configuration.ServiceConfiguration;
import rs.raf.gym.commons.dto.user.UserAuthorizationDto;
import rs.raf.gym.commons.dto.user_training.UserTrainingDto;
import rs.raf.gym.commons.dto.user_training.UserTrainingUpdateDto;
import rs.raf.gym.commons.exception.GymException;
import rs.raf.gym.commons.utils.NetworkUtils;
import rs.raf.gym.exception.ExceptionType;
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
    private final NetworkUtils            networkUtils;
    private final ServiceConfiguration    configuration;

    @Override
    public Page<UserTrainingDto> findAll(String training, Long clientId, Pageable pageable) {
        UserTrainingSpecification specification = new UserTrainingSpecification(training, clientId);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toUserTrainingDto);
    }

    @Override
    public UserTrainingDto update(UserTrainingUpdateDto updateDto, String token) throws GymException {
        Long clientId = networkUtils.request(HttpMethod.GET, "/user/id", configuration.token, new UserAuthorizationDto(token), Long.class);

        Training training = trainingRepository.findByName(updateDto.getTrainingName())
                                              .orElseThrow(() -> new GymException(ExceptionType.UPDATE_USER_TRAINING_NOT_FOUND_TRAINING,
                                                                                  updateDto.getTrainingName()));

        UserTraining userTraining = repository.findByTrainingAndClientId(training, clientId)
                                              .orElse(null);

        if (userTraining == null) {
            userTraining = new UserTraining();
            userTraining.setTraining(training);
            userTraining.setClientId(clientId);
            userTraining.setCount(0);
        }

        userTraining.setCount((userTraining.getCount() + 1) / training.getLoyalty());
        mapper.map(userTraining, updateDto);

        return mapper.toUserTrainingDto(repository.save(userTraining));
    }

}

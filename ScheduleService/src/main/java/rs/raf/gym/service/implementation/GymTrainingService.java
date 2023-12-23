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
import org.springframework.stereotype.Service;
import rs.raf.gym.dto.gym_training.GymTrainingCreateDto;
import rs.raf.gym.dto.gym_training.GymTrainingDto;
import rs.raf.gym.dto.gym_training.GymTrainingUpdateDto;
import rs.raf.gym.mapper.GymTrainingMapper;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.composite.GymTrainingComposite;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.repository.IGymTrainingRepository;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.IGymTrainingService;
import rs.raf.gym.specification.GymTrainingSpecification;

@Service
public class GymTrainingService implements IGymTrainingService {

    private final IGymTrainingRepository repository;
    private final IGymRepository         gymRepository;
    private final ITrainingRepository    trainingRepository;
    private final GymTrainingMapper      mapper;

    public GymTrainingService(IGymTrainingRepository repository, IGymRepository gymRepository, ITrainingRepository trainingRepository, GymTrainingMapper mapper) {
        this.repository         = repository;
        this.gymRepository      = gymRepository;
        this.trainingRepository = trainingRepository;
        this.mapper             = mapper;
    }

    @Override
    public Page<GymTrainingDto> findAll(String gym, String training, Double price, Integer minParticipants,
                                        Integer maxParticipants, Pageable pageable) {
        GymTrainingSpecification specification = new GymTrainingSpecification(gym, training, price, minParticipants,
                                                                              maxParticipants);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toGymTrainingDto);
    }

    @Override
    public GymTrainingDto create(GymTrainingCreateDto createDto) {
        Gym gym = gymRepository.findById(createDto.getGymName())
                               .orElse(null);

        Training training = trainingRepository.findById(createDto.getTrainingName())
                                              .orElse(null);

        if (gym == null || training == null)
            return null;

        GymTraining gymTraining = new GymTraining();
        gymTraining.setGymTraining(new GymTrainingComposite(gym, training));
        mapper.update(gymTraining, createDto);

        return mapper.toGymTrainingDto(repository.save(gymTraining));
    }

    @Override
    public GymTrainingDto update(GymTrainingUpdateDto updateDto) {
        Gym gym = gymRepository.findById(updateDto.getGymName())
                               .orElse(null);

        Training training = trainingRepository.findById(updateDto.getTrainingName())
                                              .orElse(null);

        if (gym == null || training == null) //TODO: Replace with exception
            return null;

        GymTrainingComposite gymTrainingComposite = new GymTrainingComposite();
        gymTrainingComposite.setGym(gym);
        gymTrainingComposite.setTraining(training);

        GymTraining gymTraining = repository.findById(gymTrainingComposite)
                                            .orElse(null);

        if (gymTraining == null) //TODO: Replace with exception
            return null;

        mapper.update(gymTraining, updateDto);

        return mapper.toGymTrainingDto(repository.save(gymTraining));
    }

}

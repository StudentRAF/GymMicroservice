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
import rs.raf.gym.dto.TrainingCreateDto;
import rs.raf.gym.dto.TrainingDto;
import rs.raf.gym.dto.TrainingUpdateDto;
import rs.raf.gym.mapper.TrainingMapper;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingType;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.ITrainingService;

import java.util.List;

@Service
public class TrainingService implements ITrainingService {

    private final ITrainingRepository trainingRepository;
    private final TrainingMapper      trainingMapper;

    public TrainingService(ITrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper     = trainingMapper;
    }


    @Override
    public List<TrainingDto> findAll() {
        return trainingRepository.findAll()
                                 .stream()
                                 .map(trainingMapper::toTrainingDto)
                                 .toList();
    }

    @Override
    public Page<TrainingDto> findAll(Pageable pageable) {
        return trainingRepository.findAll(pageable)
                                 .map(trainingMapper::toTrainingDto);
    }

    @Override
    public List<TrainingDto> findByType(TrainingType trainingType) {
        return trainingRepository.findByType(trainingType)
                                 .stream()
                                 .map(trainingMapper::toTrainingDto)
                                 .toList();
    }

    @Override
    public Page<TrainingDto> findByType(TrainingType trainingType, Pageable pageable) {
        return trainingRepository.findByType(trainingType, pageable)
                                 .map(trainingMapper::toTrainingDto);
    }

    @Override
    public TrainingDto findByName(String name) {
        return trainingRepository.findById(name)
                                 .map(trainingMapper::toTrainingDto)
                                 .orElse(null);
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

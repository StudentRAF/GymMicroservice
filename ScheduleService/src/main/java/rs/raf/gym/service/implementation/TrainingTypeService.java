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
import rs.raf.gym.dto.training_type.TrainingTypeCreateDto;
import rs.raf.gym.dto.training_type.TrainingTypeDto;
import rs.raf.gym.dto.training_type.TrainingTypeUpdateDto;
import rs.raf.gym.mapper.TrainingTypeMapper;
import rs.raf.gym.model.TrainingType;
import rs.raf.gym.repository.ITrainingTypeRepository;
import rs.raf.gym.service.ITrainingTypeService;
import rs.raf.gym.specification.TrainingTypeSpecification;

@Service
public class TrainingTypeService implements ITrainingTypeService {

    private final ITrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper      trainingTypeMapper;

    public TrainingTypeService(ITrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper     = trainingTypeMapper;
    }

    @Override
    public Page<TrainingTypeDto> findAll(String name, Pageable pageable) {
        Specification<TrainingType> specification = TrainingTypeSpecification.get(name);

        return trainingTypeRepository.findAll(specification, pageable)
                                     .map(trainingTypeMapper::toTrainingTypeDto);
    }

    @Override
    public TrainingTypeDto create(TrainingTypeCreateDto trainingTypeCreateDto) {
        TrainingType trainingType = trainingTypeMapper.toTrainingType(trainingTypeCreateDto);

        return trainingTypeMapper.toTrainingTypeDto(trainingTypeRepository.save(trainingType));
    }

    @Override
    public TrainingTypeDto update(TrainingTypeUpdateDto trainingTypeUpdateDto) {
        TrainingType trainingType = trainingTypeRepository.findById(trainingTypeUpdateDto.getOldName())
                                                          .orElse(null);

        if (trainingType == null)
            return null;

        if (trainingType.getName().equals(trainingTypeUpdateDto.getName()))
            return trainingTypeMapper.toTrainingTypeDto(trainingType);

        trainingTypeRepository.delete(trainingType);

        trainingType = trainingTypeMapper.toTrainingType(trainingTypeUpdateDto);

        return trainingTypeMapper.toTrainingTypeDto(trainingTypeRepository.save(trainingType));
    }

}

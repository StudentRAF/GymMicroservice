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

package rs.raf.gym.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.gym.dto.training.TrainingCreateDto;
import rs.raf.gym.dto.training.TrainingDto;
import rs.raf.gym.dto.training.TrainingUpdateDto;
import rs.raf.gym.model.TrainingType;

import java.util.List;

public interface ITrainingService {

    List<TrainingDto> findAll();

    Page<TrainingDto> findAll(Pageable pageable);

    List<TrainingDto> findByType(TrainingType trainingType);

    Page<TrainingDto> findByType(TrainingType trainingType, Pageable pageable);

    TrainingDto findByName(String name);

    TrainingDto create(TrainingCreateDto trainingCreateDto);

    TrainingDto update(TrainingUpdateDto trainingUpdateDto);

}
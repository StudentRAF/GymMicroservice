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
import rs.raf.gym.commons.dto.gym.GymCreateDto;
import rs.raf.gym.commons.dto.gym.GymDto;
import rs.raf.gym.commons.dto.gym.GymUpdateDto;
import rs.raf.gym.commons.exception.GymException;
import rs.raf.gym.exception.ExceptionType;
import rs.raf.gym.mapper.GymMapper;
import rs.raf.gym.model.Gym;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.service.IGymService;
import rs.raf.gym.specification.GymSpecification;

@Service
@AllArgsConstructor
public class GymService implements IGymService {

    private final IGymRepository repository;
    private final GymMapper      mapper;

    @Override
    public Page<GymDto> findAll(String name, Integer managerId, Pageable pageable) {
        GymSpecification specification = new GymSpecification(name, managerId);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toGymDto);
    }

    @Override
    public GymDto create(GymCreateDto createDto) {
        Gym gym = new Gym();

        mapper.map(gym, createDto);

        return mapper.toGymDto(repository.save(gym));
    }

    @Override
    public GymDto update(GymUpdateDto updateDto) throws GymException {
        Gym gym = repository.findByName(updateDto.getOldName())
                            .orElseThrow(() -> new GymException(ExceptionType.UPDATE_GYM_NOT_FOUND_GYM,
                                                                updateDto.getOldName()));

        mapper.map(gym, updateDto);

        return mapper.toGymDto(repository.save(gym));
    }

    @Override
    public Long findId(String gymName) {
        Gym gym = repository.findByName(gymName)
                            .orElseThrow(() -> new GymException(ExceptionType.FIND_GYM_ID_NOT_FOUND_GYM,
                                                                gymName));

        return gym.getId();
    }

}

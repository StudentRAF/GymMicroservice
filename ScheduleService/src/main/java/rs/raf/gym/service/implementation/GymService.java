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
import rs.raf.gym.dto.gym.GymCreateDto;
import rs.raf.gym.dto.gym.GymDto;
import rs.raf.gym.dto.gym.GymUpdateDto;
import rs.raf.gym.mapper.GymMapper;
import rs.raf.gym.model.Gym;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.service.IGymService;
import rs.raf.gym.specification.GymSpecification;

@Service
public class GymService implements IGymService {

    private final IGymRepository gymRepository;
    private final GymMapper      gymMapper;

    public GymService(IGymRepository gymRepository, GymMapper gymMapper) {
        this.gymRepository = gymRepository;
        this.gymMapper     = gymMapper;
    }

    @Override
    public Page<GymDto> findAll(String name, Integer managerId, Pageable pageable) {
        Specification<Gym> specification = GymSpecification.get(name, managerId);
        
        return gymRepository.findAll(specification, pageable).map(gymMapper::toGymDto);
    }

    @Override
    public GymDto create(GymCreateDto gymCreateDto) {
        Gym gym = gymMapper.toGym(gymCreateDto);

        return gymMapper.toGymDto(gymRepository.save(gym));
    }

    @Override
    public GymDto update(GymUpdateDto gymUpdateDto) {
        Gym gym = gymRepository.findById(gymUpdateDto.getOldName())
                               .orElse(null);

        if (gym == null)
            return null;

        if (!gym.getName().equals(gymUpdateDto.getName()))
            gymRepository.delete(gym);

        gym = gymMapper.toGym(gymUpdateDto);

        return gymMapper.toGymDto(gymRepository.save(gym));
    }

}

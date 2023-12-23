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

package rs.raf.gym.mapper;

import org.springframework.stereotype.Component;
import rs.raf.gym.dto.gym.GymCreateDto;
import rs.raf.gym.dto.gym.GymDto;
import rs.raf.gym.dto.gym.GymUpdateDto;
import rs.raf.gym.model.Gym;

@Component
public class GymMapper {

    /**
     * Maps Gym to GymDto object.
     * @param gym gym
     * @return GymDto object
     */
    public GymDto mapGymDto(Gym gym) {
        GymDto gymDto = new GymDto();

        gymDto.setName(gym.getName());
        gymDto.setDescription(gym.getDescription());
        gymDto.setManagerId(gym.getManagerId());
        gymDto.setTrainers(gym.getTrainers());

        return gymDto;
    }

    /**
     * Maps GymCreateDto to existing Gym object.
     * @param gym gym
     * @param createDto create dto
     * @return Gym object
     */
    public Gym map(Gym gym, GymCreateDto createDto) {
        gym.setName(createDto.getName());
        gym.setDescription(createDto.getDescription());
        gym.setTrainers(createDto.getTrainers());

        return gym;
    }

    /**
     * Maps GymUpdateDto to existing Gym object.
     * @param gym gym
     * @param updateDto update dto
     * @return Gym object
     */
    public Gym map(Gym gym, GymUpdateDto updateDto) {
        gym.setName(updateDto.getName());
        gym.setDescription(updateDto.getDescription());
        gym.setManagerId(updateDto.getManagerId());
        gym.setTrainers(updateDto.getTrainers());

        return gym;
    }

}

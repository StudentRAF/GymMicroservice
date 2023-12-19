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
import rs.raf.gym.dto.GymCreateDto;
import rs.raf.gym.dto.GymDto;
import rs.raf.gym.dto.GymUpdateDto;
import rs.raf.gym.model.Gym;

@Component
public class GymMapper {

    public GymDto toGymDto(Gym gym) {
        GymDto gymDto = new GymDto();

        gymDto.setName(gym.getName());
        gymDto.setDescription(gym.getDescription());
        gymDto.setManagerId(gym.getManagerId());
        gymDto.setTrainers(gym.getTrainers());

        return gymDto;
    }

    public Gym toGym(GymCreateDto gymCreateDto) {
        Gym gym = new Gym();

        gym.setName(gymCreateDto.getName());
        gym.setDescription(gymCreateDto.getDescription());
        gym.setTrainers(gymCreateDto.getTrainers());

        return gym;
    }

    public Gym toGym(GymUpdateDto gymUpdateDto) {
        Gym gym = new Gym();

        gym.setName(gymUpdateDto.getName());
        gym.setDescription(gymUpdateDto.getDescription());
        gym.setManagerId(gymUpdateDto.getManagerId());
        gym.setTrainers(gymUpdateDto.getTrainers());

        return gym;
    }

}

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

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import rs.raf.gym.ServiceOrigin;
import rs.raf.gym.commons.dto.gym.GymCreateDto;
import rs.raf.gym.commons.dto.gym.GymDto;
import rs.raf.gym.commons.dto.gym.GymUpdateDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.utils.NetworkUtils;
import rs.raf.gym.model.Gym;

@Component
@AllArgsConstructor
public class GymMapper {

    private final NetworkUtils networkUtils;

    /**
     * Maps Gym to GymDto object.
     * @param gym gym
     * @return GymDto object
     */
    public GymDto toGymDto(Gym gym) {
        return new GymDto(gym.getName(),
                          gym.getDescription(),
                          gym.getManagerId() != null ? networkUtils.request(HttpMethod.GET, "/user/" + gym.getManagerId(), ServiceOrigin.TOKEN, UserDto.class)
                                                     : null,
                          gym.getTrainers());
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
        gym.setTrainers(updateDto.getTrainers());

        return gym;
    }

}

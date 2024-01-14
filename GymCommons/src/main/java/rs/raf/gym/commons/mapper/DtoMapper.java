/*
 * Copyright (C) 2024. Lazar Dobrota and Nemanja Radovanovic
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

package rs.raf.gym.commons.mapper;

import org.springframework.stereotype.Component;
import rs.raf.gym.commons.dto.gym.GymDto;
import rs.raf.gym.commons.dto.gym.GymNoManagerDto;
import rs.raf.gym.commons.dto.manager.ManagerNoGymDto;
import rs.raf.gym.commons.dto.user.UserDto;

@Component
public class DtoMapper {

    public ManagerNoGymDto toManagerNoGymDto(UserDto userDto) {
        ManagerNoGymDto managerNoGymDto = new ManagerNoGymDto();

        managerNoGymDto.setFirstname(userDto.getFirstname());
        managerNoGymDto.setLastname(userDto.getLastname());
        managerNoGymDto.setUsername(userDto.getUsername());
        managerNoGymDto.setEmail(userDto.getEmail());
        managerNoGymDto.setDateOfBirth(userDto.getDateOfBirth());
        managerNoGymDto.setRecruitmentDate(userDto.getRecruitmentDate());
        managerNoGymDto.setUserRole(userDto.getUserRole());

        return managerNoGymDto;
    }

    public GymNoManagerDto toGymNoManagerDto(GymDto gymDto) {
        GymNoManagerDto gymNoManagerDto = new GymNoManagerDto();

        gymNoManagerDto.setName(gymDto.getName());
        gymNoManagerDto.setDescription(gymDto.getDescription());
        gymNoManagerDto.setTrainers(gymDto.getTrainers());

        return gymNoManagerDto;
    }

}

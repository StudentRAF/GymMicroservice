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
import org.springframework.stereotype.Component;
import rs.raf.gym.commons.dto.client.ClientCreateDto;
import rs.raf.gym.commons.dto.client.ClientDto;
import rs.raf.gym.commons.dto.client.ClientUpdateDto;
import rs.raf.gym.commons.dto.manager.ManagerCreateDto;
import rs.raf.gym.commons.dto.manager.ManagerDto;
import rs.raf.gym.commons.dto.manager.ManagerUpdateDto;
import rs.raf.gym.commons.dto.user.AdminCreateDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.dto.user.UserUpdateDto;
import rs.raf.gym.model.User;

@Component
@AllArgsConstructor
public class UserMapper {

    private final UserRoleMapper userRoleMapper;

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserRole(userRoleMapper.userRoleToUserRoleDto(user.getUserRole()));
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setMembershipId(user.getMembershipId());
        userDto.setGymId(user.getGymId());
        userDto.setRecruitmentDate(user.getRecruitmentDate());
        userDto.setAccess(user.isAccess());
        userDto.setActivated(user.isActivated());

        return userDto;
    }

    public ClientDto userToClientDto(User user) {
        ClientDto clientDto = new ClientDto();

        clientDto.setUserRole(userRoleMapper.userRoleToUserRoleDto(user.getUserRole()));
        clientDto.setFirstname(user.getFirstname());
        clientDto.setLastname(user.getLastname());
        clientDto.setUsername(user.getUsername());
        clientDto.setEmail(user.getEmail());
        clientDto.setDateOfBirth(user.getDateOfBirth());
        clientDto.setMembershipId(user.getMembershipId());

        return clientDto;
    }

    public ManagerDto userToManagerDto(User user) {
        ManagerDto managerDto = new ManagerDto();

        managerDto.setUserRole(userRoleMapper.userRoleToUserRoleDto(user.getUserRole()));
        managerDto.setFirstname(user.getFirstname());
        managerDto.setLastname(user.getLastname());
        managerDto.setUsername(user.getUsername());
        managerDto.setEmail(user.getEmail());
        managerDto.setDateOfBirth(user.getDateOfBirth());
        managerDto.setGymId(user.getGymId());
        managerDto.setRecruitmentDate(user.getRecruitmentDate());

        return managerDto;
    }

    public User mapUser(User user, UserUpdateDto userUpdateDto) {
        user.setFirstname(userUpdateDto.getFirstname());
        user.setLastname(userUpdateDto.getLastname());
        user.setUsername(userUpdateDto.getUsername());
        user.setPassword(userUpdateDto.getPassword());
        user.setEmail(userUpdateDto.getEmail());
        user.setDateOfBirth(userUpdateDto.getDateOfBirth());

        return user;
    }

    public User mapUser(User user, AdminCreateDto adminCreateDto) {
        user.setFirstname(adminCreateDto.getFirstname());
        user.setLastname(adminCreateDto.getLastname());
        user.setUsername(adminCreateDto.getUsername());
        user.setPassword(adminCreateDto.getPassword());
        user.setEmail(adminCreateDto.getEmail());
        user.setDateOfBirth(adminCreateDto.getDateOfBirth());
        user.setGymId(adminCreateDto.getGymId());
        user.setAccess(adminCreateDto.isAccess());
        user.setActivated(adminCreateDto.isActivated());

        return user;
    }

    public User mapUser(User user, ClientCreateDto clientCreateDto) {
        user.setFirstname(clientCreateDto.getFirstname());
        user.setLastname(clientCreateDto.getLastname());
        user.setUsername(clientCreateDto.getUsername());
        user.setPassword(clientCreateDto.getPassword());
        user.setEmail(clientCreateDto.getEmail());
        user.setDateOfBirth(clientCreateDto.getDateOfBirth());

        return user;
    }

    public User mapUser(User user, ClientUpdateDto clientUpdateDto) {
        user.setFirstname(clientUpdateDto.getFirstname());
        user.setLastname(clientUpdateDto.getLastname());
        user.setUsername(clientUpdateDto.getUsername());
        user.setPassword(clientUpdateDto.getPassword());
        user.setEmail(clientUpdateDto.getEmail());
        user.setDateOfBirth(clientUpdateDto.getDateOfBirth());

        return user;
    }

    public User mapUser(User user, ManagerCreateDto managerCreateDto) {
        user.setFirstname(managerCreateDto.getFirstname());
        user.setLastname(managerCreateDto.getLastname());
        user.setUsername(managerCreateDto.getUsername());
        user.setPassword(managerCreateDto.getPassword());
        user.setEmail(managerCreateDto.getEmail());
        user.setDateOfBirth(managerCreateDto.getDateOfBirth());
        user.setGymId(managerCreateDto.getGymId());

        return user;
    }

    public User mapUser(User user, ManagerUpdateDto managerUpdateDto) {
        user.setFirstname(managerUpdateDto.getFirstname());
        user.setLastname(managerUpdateDto.getLastname());
        user.setUsername(managerUpdateDto.getUsername());
        user.setPassword(managerUpdateDto.getPassword());
        user.setEmail(managerUpdateDto.getEmail());
        user.setDateOfBirth(managerUpdateDto.getDateOfBirth());
        user.setGymId(managerUpdateDto.getGymId());

        return user;
    }

}

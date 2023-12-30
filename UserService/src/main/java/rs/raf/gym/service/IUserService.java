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
import rs.raf.gym.commons.dto.client.ClientCreateDto;
import rs.raf.gym.commons.dto.client.ClientDto;
import rs.raf.gym.commons.dto.client.ClientUpdateDto;
import rs.raf.gym.commons.dto.manager.ManagerCreateDto;
import rs.raf.gym.commons.dto.manager.ManagerDto;
import rs.raf.gym.commons.dto.manager.ManagerUpdateDto;
import rs.raf.gym.commons.dto.user.UserCreateDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.dto.user.UserLoginDto;
import rs.raf.gym.commons.dto.user.UserTokenDto;
import rs.raf.gym.commons.dto.user.UserUpdateDto;

public interface IUserService {

    //Find all users on specific page
    Page<UserDto> getAllUsers(String role, String firstname, String lastname, String username, Pageable pageable);

    UserDto findById(Long id);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUser(UserUpdateDto userUpdateDto);

    ClientDto createClient(ClientCreateDto clientCreateDto);

    ClientDto updateClient(ClientUpdateDto clientUpdateDto);

    ManagerDto createManager(ManagerCreateDto managerCreateDto);

    ManagerDto updateManager(ManagerUpdateDto managerUpdateDto);

    String login(UserLoginDto userLoginDto);

    String getRole(String token);
}

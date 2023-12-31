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
import rs.raf.gym.commons.dto.user.AdminCreateDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.dto.user.UserLoginDto;
import rs.raf.gym.commons.dto.user.UserUpdateDto;
import rs.raf.gym.commons.exception.GymException;

public interface IUserService {

    //Find all users on specific page
    Page<UserDto> getAllUsers(String role, String firstname, String lastname, String username, Pageable pageable);

    UserDto findById(Long id) throws GymException;

    UserDto createAdmin(AdminCreateDto adminCreateDto) throws GymException;

    UserDto updateUser(UserUpdateDto userUpdateDto) throws GymException;

    ClientDto createClient(ClientCreateDto clientCreateDto) throws GymException;

    ClientDto updateClient(ClientUpdateDto clientUpdateDto) throws GymException;

    ManagerDto createManager(ManagerCreateDto managerCreateDto) throws GymException;

    ManagerDto updateManager(ManagerUpdateDto managerUpdateDto) throws GymException;

    String login(UserLoginDto userLoginDto) throws GymException;
}

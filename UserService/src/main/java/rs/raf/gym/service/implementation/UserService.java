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
import org.springframework.transaction.annotation.Transactional;
import rs.raf.gym.commons.dto.client.ClientCreateDto;
import rs.raf.gym.commons.dto.client.ClientDto;
import rs.raf.gym.commons.dto.client.ClientUpdateDto;
import rs.raf.gym.commons.dto.manager.ManagerCreateDto;
import rs.raf.gym.commons.dto.manager.ManagerDto;
import rs.raf.gym.commons.dto.manager.ManagerUpdateDto;
import rs.raf.gym.commons.dto.user.UserCreateDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.dto.user.UserLoginDto;
import rs.raf.gym.commons.dto.user.UserUpdateDto;
import rs.raf.gym.mapper.UserMapper;
import rs.raf.gym.model.Roles;
import rs.raf.gym.model.User;
import rs.raf.gym.model.UserRole;
import rs.raf.gym.repository.IUserRepository;
import rs.raf.gym.repository.IUserRoleRepository;
import rs.raf.gym.security.SecurityAspect;
import rs.raf.gym.security.service.ITokenService;
import rs.raf.gym.service.IUserService;
import rs.raf.gym.specification.UserSpecification;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository     userRepository;
    private final IUserRoleRepository userRoleRepository;
    private final UserMapper          userMapper;
    private final ITokenService       tokenService;
    private final SecurityAspect      securityAspect;

    @Override
    public Page<UserDto> getAllUsers(String role, String firstname, String lastname, String username, Pageable pageable) {
        UserSpecification specification = new UserSpecification(role, firstname, lastname, username);

        return userRepository.findAll(specification.filter(), pageable)
                                                   .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.userToUserDto(userRepository.findById(id).get());
    }

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        User user = new User();
        userMapper.mapUser(user, userCreateDto);
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public ClientDto createClient(ClientCreateDto clientCreateDto) {
        UserRole userRole = userRoleRepository.findByName(Roles.CLIENT.getName())
                                              .orElse(null);
        User user = new User();
        user.setUserRole(userRole);
        userMapper.mapUser(user, clientCreateDto);
        return userMapper.userToClientDto(userRepository.save(user));
    }

    @Override
    public ManagerDto createManager(ManagerCreateDto managerCreateDto) {
        UserRole userRole = userRoleRepository.findByName(Roles.MANAGER.getName())
                                              .orElse(null);
        User user = new User();
        user.setUserRole(userRole);
        userMapper.mapUser(user, managerCreateDto);
        return userMapper.userToManagerDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findUserByUsername(userUpdateDto.getOldUsername())
                                  .orElse(null);

        if (user == null)
            return null;

        userMapper.mapUser(user, userUpdateDto);

        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public ClientDto updateClient(ClientUpdateDto clientUpdateDto) {
        User user = userRepository.findUserByUsername(clientUpdateDto.getOldUsername())
                                  .orElse(null);

        if (user == null)
            return null;

        userMapper.mapUser(user, clientUpdateDto);

        return userMapper.userToClientDto(userRepository.save(user));
    }


    @Override
    public ManagerDto updateManager(ManagerUpdateDto managerUpdateDto) {
        User user = userRepository.findUserByUsername(managerUpdateDto.getOldUsername())
                                  .orElse(null);

        if (user == null)
            return null;

        userMapper.mapUser(user, managerUpdateDto);

        return userMapper.userToManagerDto(userRepository.save(user));
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        User user = userRepository.findUserByUsernameAndPassword(userLoginDto.getUsername(), userLoginDto.getPassword())
                                  .orElse(null);

        if (user == null || !user.isActivated() || !user.isAccess())
            return null;

        //Create token
        Map<String, Object> payload = new HashMap<>();
        payload.put(User.id(), user.getId());
        payload.put(User.userRole(), user.getUserRole().getName());

        return tokenService.encrypt(payload);
    }

    @Override
    public String getRole(String token) {
        Roles role = securityAspect.getRole(token);
        return role != null ? role.getName() : null;
    }
}

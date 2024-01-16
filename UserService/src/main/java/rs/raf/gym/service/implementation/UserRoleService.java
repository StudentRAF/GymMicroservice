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
import rs.raf.gym.commons.dto.user_role.UserRoleCreateDto;
import rs.raf.gym.commons.dto.user_role.UserRoleDto;
import rs.raf.gym.commons.dto.user_role.UserRoleUpdateDto;
import rs.raf.gym.commons.exception.GymException;
import rs.raf.gym.exception.ExceptionType;
import rs.raf.gym.mapper.UserRoleMapper;
import rs.raf.gym.commons.model.Role;
import rs.raf.gym.model.UserRole;
import rs.raf.gym.repository.IUserRoleRepository;
import rs.raf.gym.commons.security.SecurityAspect;
import rs.raf.gym.service.IUserRoleService;
import rs.raf.gym.specification.UserRoleSpecification;

@Service
@AllArgsConstructor
public class UserRoleService implements IUserRoleService {

    private final IUserRoleRepository userRoleRepository;
    private final UserRoleMapper      userRoleMapper;
    private final SecurityAspect      securityAspect;

    @Override
    public Page<UserRoleDto> getAllUserRoles(String role, Pageable pageable) {
        UserRoleSpecification specification = new UserRoleSpecification(role);
        return userRoleRepository.findAll(specification.filter(), pageable).map(userRoleMapper::userRoleToUserRoleDto);
    }

    @Override
    public UserRoleDto createUserRole(UserRoleCreateDto userRoleCreateDto) {
        UserRole userRole = new UserRole();
        userRoleMapper.mapUserRole(userRole, userRoleCreateDto);
        return userRoleMapper.userRoleToUserRoleDto(userRoleRepository.save(userRole));
    }

    @Override
    public UserRoleDto updateUserRole(UserRoleUpdateDto userRoleUpdateDto) throws GymException {
        UserRole userRole = userRoleRepository.findByName(userRoleUpdateDto.getOldName())
                .orElseThrow(() -> new GymException(ExceptionType.UPDATE_USER_ROLE_NOT_FOUND_USER_ROLE, userRoleUpdateDto.getOldName()));


        userRoleMapper.mapUserRole(userRole, userRoleUpdateDto);
        return userRoleMapper.userRoleToUserRoleDto(userRoleRepository.save(userRole));
    }

    @Override
    public String findRole(String token) throws GymException {
        Role role = securityAspect.getRole(token);
        if (role == null) throw new GymException(ExceptionType.FIND_ROLE_NOT_FOUND_USER_ROLE);
        return role.getName();
    }

}

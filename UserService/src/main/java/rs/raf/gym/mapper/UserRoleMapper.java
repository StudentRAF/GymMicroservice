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
import rs.raf.gym.commons.dto.user_role.UserRoleCreateDto;
import rs.raf.gym.commons.dto.user_role.UserRoleDto;
import rs.raf.gym.commons.dto.user_role.UserRoleUpdateDto;
import rs.raf.gym.model.UserRole;

@Component
public class UserRoleMapper {

     public UserRoleDto userRoleToUserRoleDto(UserRole userRole) {
         UserRoleDto userRoleDto = new UserRoleDto();

         userRoleDto.setName(userRole.getName());

         return userRoleDto;
     }

    public void mapUserRole(UserRole userRole, UserRoleCreateDto userRoleCreateDto) {
         userRole.setName(userRoleCreateDto.getName());
    }

    public void mapUserRole(UserRole userRole, UserRoleUpdateDto userRoleUpdateDto) {
        userRole.setName(userRoleUpdateDto.getName());
    }
}

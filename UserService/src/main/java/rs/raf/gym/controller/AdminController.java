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

package rs.raf.gym.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.dto.user.UserUpdateDto;
import rs.raf.gym.commons.dto.userRole.UserRoleCreateDto;
import rs.raf.gym.commons.dto.userRole.UserRoleDto;
import rs.raf.gym.commons.dto.userRole.UserRoleUpdateDto;
import rs.raf.gym.service.IUserRoleService;
import rs.raf.gym.service.IUserService;

@AllArgsConstructor
@RestController
public class AdminController {

    private final IUserService     userService;
    private final IUserRoleService userRoleService;

    @GetMapping(value = "/all")
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(name = "role",      required = false) String role,
                                                     @RequestParam(name = "firstname", required = false) String firstname,
                                                     @RequestParam(name = "lastname",  required = false) String lastname,
                                                     @RequestParam(name = "username",  required = false) String username,
                                                     Pageable pageable) {
        return new ResponseEntity<>(userService.getAllUsers(role, firstname, lastname, username, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        return new ResponseEntity<>(userService.updateUser(userUpdateDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/role/all")
    public ResponseEntity<Page<UserRoleDto>> getAllUserRoles(@RequestParam(name = "role", required = false) String role,
                                                       Pageable pageable) {
        return new ResponseEntity<>(userRoleService.getAllUserRoles(role, pageable), HttpStatus.OK);
    }

    @PostMapping("/role")
    public ResponseEntity<UserRoleDto> createUserRole(@RequestBody @Valid UserRoleCreateDto userRoleCreateDto) {
        return new ResponseEntity<>(userRoleService.createUserRole(userRoleCreateDto), HttpStatus.ACCEPTED);
    }

    @PutMapping("/role")
    public ResponseEntity<UserRoleDto> updateUserRole(@RequestBody @Valid UserRoleUpdateDto userRoleUpdateDto) {
        return new ResponseEntity<>(userRoleService.updateUserRole(userRoleUpdateDto), HttpStatus.ACCEPTED);
    }

}

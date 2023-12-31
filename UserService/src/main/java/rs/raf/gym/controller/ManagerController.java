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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.commons.dto.manager.ManagerCreateDto;
import rs.raf.gym.commons.dto.manager.ManagerDto;
import rs.raf.gym.commons.dto.manager.ManagerUpdateDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.model.Roles;
import rs.raf.gym.security.CheckSecurity;
import rs.raf.gym.service.IUserService;

@AllArgsConstructor
@RestController
@RequestMapping("/manager")
public class ManagerController {
    private final IUserService userService;

    @PostMapping("/register")
    @CheckSecurity(roles = Roles.ADMIN)
    public ResponseEntity<ManagerDto> createManager(@RequestBody @Valid ManagerCreateDto managerCreateDto,
                                                    @RequestHeader(name = "authorization") String authorization) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userService.createManager(managerCreateDto), HttpStatus.CREATED));
    }

    @PutMapping("/update")
    @CheckSecurity(roles = Roles.MANAGER)
    public ResponseEntity<ManagerDto> updateManager(@RequestBody @Valid ManagerUpdateDto managerUpdateDto,
                                                    @RequestHeader(name = "authorization") String authorization) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userService.updateManager(managerUpdateDto), HttpStatus.ACCEPTED));
    }
}

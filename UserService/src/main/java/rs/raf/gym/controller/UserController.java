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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.commons.dto.user.UserLoginDto;
import rs.raf.gym.commons.dto.user.UserTokenDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.service.IUserRoleService;
import rs.raf.gym.service.IUserService;

@AllArgsConstructor
@RestController
public class UserController {

    private final IUserService userService;
    private final IUserRoleService userRoleService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userService.login(userLoginDto), HttpStatus.OK));
    }

    @GetMapping("/role")
    public ResponseEntity<String> getRole(@RequestHeader(name = "authorization") String token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userRoleService.findRole(token), HttpStatus.OK));
    }

    @GetMapping("/id")
    public ResponseEntity<Long> getId(@RequestHeader(name = "authorization") String token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userService.findIdByToken(token), HttpStatus.OK));
    }

}

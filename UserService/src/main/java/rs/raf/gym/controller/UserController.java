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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.commons.dto.user.UserAuthorizationDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.dto.user.UserLoginDto;
import rs.raf.gym.commons.dto.user.UserTokenDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.model.Roles;
import rs.raf.gym.security.CheckSecurity;
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
    @CheckSecurity(roles = Roles.SERVICE)
    public ResponseEntity<String> getRole( @RequestBody @Valid UserAuthorizationDto userAuthorizationDto, @RequestHeader(name = "authorization") String token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userRoleService.findRole(userAuthorizationDto.getToken()), HttpStatus.OK));
    }

    @GetMapping("/id")
    @CheckSecurity(roles = Roles.SERVICE)
    public ResponseEntity<Long> getId(@RequestBody @Valid UserAuthorizationDto userAuthorizationDto, @RequestHeader(name = "authorization") String token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userService.findIdByToken(userAuthorizationDto.getToken()), HttpStatus.OK));
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<UserDto> activateAccount(@PathVariable("token") String token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(userService.activateAccount(token), HttpStatus.ACCEPTED));
    }

}

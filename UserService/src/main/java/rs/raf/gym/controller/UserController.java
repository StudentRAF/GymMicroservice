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
import org.springframework.web.bind.annotation.*;
import rs.raf.gym.dto.user.UserDto;
import rs.raf.gym.dto.user.UserLoginDto;
import rs.raf.gym.dto.user.UserTokenDto;
import rs.raf.gym.dto.user.UserUpdateDto;
import rs.raf.gym.service.IUserService;

@AllArgsConstructor
@RestController
public class UserController {

    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        return new ResponseEntity<>(userService.login(userLoginDto), HttpStatus.OK);
    }

}

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.commons.dto.training.TrainingCreateDto;
import rs.raf.gym.commons.dto.training.TrainingDto;
import rs.raf.gym.commons.dto.training.TrainingUpdateDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.commons.model.Role;
import rs.raf.gym.commons.security.CheckSecurity;
import rs.raf.gym.service.ITrainingService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/training")
public class TrainingController {

    private final ITrainingService service;

    @GetMapping("/all")
    @CheckSecurity(role = {Role.SERVICE, Role.ADMIN, Role.MANAGER})
    public ResponseEntity<List<TrainingDto>> getAll() {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.getAll(), HttpStatus.OK));
    }

    @GetMapping
    @CheckSecurity(role = {Role.SERVICE, Role.ADMIN, Role.MANAGER})
    public ResponseEntity<Page<TrainingDto>> filter(@RequestParam (name = "name",    required = false) String  name,
                                                    @RequestParam (name = "type",    required = false) String  type,
                                                    @RequestParam (name = "loyalty", required = false) Integer loyalty,
                                                    @RequestHeader(name = "authorization"            ) String  token,
                                                    Pageable pageable) {
        return new ResponseEntity<>(service.findAll(name, type, loyalty, pageable), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(role = {Role.SERVICE, Role.ADMIN, Role.MANAGER})
    public ResponseEntity<TrainingDto> create(@RequestBody @Valid                    TrainingCreateDto createDto,
                                              @RequestHeader(name = "authorization") String            token) {
        return new ResponseEntity<>(service.create(createDto), HttpStatus.CREATED);
    }

    @PutMapping
    @CheckSecurity(role = {Role.SERVICE, Role.ADMIN, Role.MANAGER})
    public ResponseEntity<TrainingDto> update(@RequestBody @Valid                    TrainingUpdateDto updateDto,
                                              @RequestHeader(name = "authorization") String            token) {
        return new ResponseEntity<>(service.update(updateDto), HttpStatus.OK);
    }

}

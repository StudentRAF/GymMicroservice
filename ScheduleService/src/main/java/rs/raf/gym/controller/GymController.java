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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.commons.dto.gym.GymCreateDto;
import rs.raf.gym.commons.dto.gym.GymDto;
import rs.raf.gym.commons.dto.gym.GymUpdateDto;
import rs.raf.gym.commons.dto.gym.GymUpdateManagerDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.service.IGymService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/gym")
public class GymController {

    private final IGymService service;

    @GetMapping("/all")
    public ResponseEntity<List<GymDto>> getAll() {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.getAll(), HttpStatus.OK));
    }

    @GetMapping
    public ResponseEntity<Page<GymDto>> filter(@RequestParam (name = "name",    required = false) String  name,
                                               @RequestParam (name = "manager", required = false) Integer manager,
                                               @RequestHeader(name = "authorization"            ) String  token,
                                               Pageable pageable) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.findAll(name, manager, pageable),HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymDto> findGym(@PathVariable (name = "id"                             ) Long    id,
                                          @RequestParam (name = "flag",          required = false) Boolean flag,
                                          @RequestHeader(name = "authorization"                  ) String  token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.findGymWithId(id, flag != null && flag), HttpStatus.OK));
    }

    @GetMapping("/id/{gym}")
    public ResponseEntity<Long> findId(@PathVariable (name = "gym"          ) String gymName,
                                       @RequestHeader(name = "authorization") String token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.findId(gymName),HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<GymDto> create(@RequestBody @Valid                    GymCreateDto createDto,
                                         @RequestHeader(name = "authorization") String       token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.create(createDto), HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<GymDto> update(@RequestBody @Valid                    GymUpdateDto updateDto,
                                         @RequestHeader(name = "authorization") String       token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.update(updateDto), HttpStatus.OK));
    }

    @PutMapping("/manager")
    public ResponseEntity<GymDto> updateManager(@RequestBody @Valid                    GymUpdateManagerDto updateDto,
                                                @RequestHeader(name = "authorization") String              token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.updateManager(updateDto), HttpStatus.OK));
    }

}

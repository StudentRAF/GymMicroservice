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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.dto.GymCreateDto;
import rs.raf.gym.dto.GymDto;
import rs.raf.gym.dto.GymUpdateDto;
import rs.raf.gym.service.IGymService;

import java.util.List;

@RestController
@RequestMapping("/gym")
public class GymController {

    private final IGymService gymService;

    public GymController(IGymService gymService) {
        this.gymService = gymService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GymDto>>getAllUsers() {
        return new ResponseEntity<>(gymService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/all", params = { "page", "size" })
    public ResponseEntity<Page<GymDto>>getAllUsers(Pageable pageable) {
        return new ResponseEntity<>(gymService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/name={name}")
    public ResponseEntity<GymDto>getUserWithName(@PathVariable("name") String name) {
        return new ResponseEntity<>(gymService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/manager={manager}")
    public ResponseEntity<GymDto>getUserWithName(@PathVariable("manager") Long managerId) {
        return new ResponseEntity<>(gymService.findByManager(managerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GymDto>createUser(@RequestBody @Valid GymCreateDto gymCreateDto) {
        return new ResponseEntity<>(gymService.create(gymCreateDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GymDto>updateUser(@RequestBody @Valid GymUpdateDto gymUpdateDto) {
        return new ResponseEntity<>(gymService.update(gymUpdateDto), HttpStatus.OK);
    }

}

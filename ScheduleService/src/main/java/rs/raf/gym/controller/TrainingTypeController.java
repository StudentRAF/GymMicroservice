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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.dto.training_type.TrainingTypeCreateDto;
import rs.raf.gym.dto.training_type.TrainingTypeDto;
import rs.raf.gym.dto.training_type.TrainingTypeUpdateDto;
import rs.raf.gym.service.ITrainingTypeService;

@AllArgsConstructor
@RestController
@RequestMapping("/training-type")
public class TrainingTypeController {

    private final ITrainingTypeService service;

    @GetMapping
    public ResponseEntity<Page<TrainingTypeDto>> filter(@RequestParam(value = "name", required = false) String name,
                                                        Pageable pageable) {
        return new ResponseEntity<>(service.findAll(name, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrainingTypeDto> create(@RequestBody @Valid TrainingTypeCreateDto createDto) {
        return new ResponseEntity<>(service.create(createDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TrainingTypeDto> update(@RequestBody @Valid TrainingTypeUpdateDto updateDto) {
        return new ResponseEntity<>(service.update(updateDto), HttpStatus.OK);
    }

}

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
import rs.raf.gym.dto.gym_training.GymTrainingCreateDto;
import rs.raf.gym.dto.gym_training.GymTrainingDto;
import rs.raf.gym.dto.gym_training.GymTrainingUpdateDto;
import rs.raf.gym.service.IGymTrainingService;

@AllArgsConstructor
@RestController
@RequestMapping("/gym-training")
public class GymTrainingController {

    private final IGymTrainingService service;

    @GetMapping
    public ResponseEntity<Page<GymTrainingDto>> filter(@RequestParam(value = "gym",      required = false) String  gym,
                                                       @RequestParam(value = "training", required = false) String  training,
                                                       @RequestParam(value = "price",    required = false) Double  price,
                                                       @RequestParam(value = "min",      required = false) Integer min,
                                                       @RequestParam(value = "max",      required = false) Integer max,
                                                       Pageable pageable) {
        return new ResponseEntity<>(service.findAll(gym, training, price, min, max, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GymTrainingDto> create(@RequestBody @Valid GymTrainingCreateDto createDto) {
        return new ResponseEntity<>(service.create(createDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GymTrainingDto> update(@RequestBody @Valid GymTrainingUpdateDto updateDto) {
        return new ResponseEntity<>(service.update(updateDto), HttpStatus.OK);
    }

}

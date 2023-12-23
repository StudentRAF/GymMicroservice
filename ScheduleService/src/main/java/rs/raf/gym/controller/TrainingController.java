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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.gym.dto.training.TrainingCreateDto;
import rs.raf.gym.dto.training.TrainingDto;
import rs.raf.gym.dto.training.TrainingUpdateDto;
import rs.raf.gym.service.ITrainingService;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private final ITrainingService trainingService;

    public TrainingController(ITrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    public ResponseEntity<Page<TrainingDto>> getAllTrainings(@RequestParam(value = "name", required = false) String name,
                                                             @RequestParam(value = "type", required = false) String type,
                                                             @RequestParam(value = "loyalty", required = false) Integer loyalty,
                                                             Pageable pageable) {
        return new ResponseEntity<>(trainingService.findAll(name, type, loyalty, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody @Valid TrainingCreateDto trainingCreateDto) {
        return new ResponseEntity<>(trainingService.create(trainingCreateDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TrainingDto> updateTraining(@RequestBody @Valid TrainingUpdateDto trainingUpdateDto) {
        return new ResponseEntity<>(trainingService.update(trainingUpdateDto), HttpStatus.OK);
    }

}

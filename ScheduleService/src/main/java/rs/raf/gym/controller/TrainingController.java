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
import rs.raf.gym.dto.TrainingCreateDto;
import rs.raf.gym.dto.TrainingDto;
import rs.raf.gym.dto.TrainingUpdateDto;
import rs.raf.gym.model.TrainingType;
import rs.raf.gym.service.ITrainingService;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private final ITrainingService trainingService;

    public TrainingController(ITrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        return new ResponseEntity<>(trainingService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/all", params = { "page", "size" })
    public ResponseEntity<Page<TrainingDto>> getAllTrainings(Pageable pageable) {
        return new ResponseEntity<>(trainingService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/type={type}")
    public ResponseEntity<List<TrainingDto>> getAllTrainingsWithType(@PathVariable("type") @Valid TrainingType trainingType) {
        return new ResponseEntity<>(trainingService.findByType(trainingType), HttpStatus.OK);
    }

    @GetMapping(value = "/type={type}", params = { "page", "size" })
    public ResponseEntity<Page<TrainingDto>> getAllTrainingsWithType(@PathVariable("type") @Valid TrainingType trainingType, Pageable pageable) {
        return new ResponseEntity<>(trainingService.findByType(trainingType, pageable), HttpStatus.OK);
    }

    @GetMapping("/name={name}")
    public ResponseEntity<TrainingDto> getTrainingWithName(@PathVariable("name") String name) {
        return new ResponseEntity<>(trainingService.findByName(name), HttpStatus.OK);
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

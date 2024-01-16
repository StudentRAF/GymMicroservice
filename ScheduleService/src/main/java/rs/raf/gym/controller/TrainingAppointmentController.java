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
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.commons.model.Role;
import rs.raf.gym.commons.security.CheckSecurity;
import rs.raf.gym.service.ITrainingAppointmentService;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@RestController
@RequestMapping("/training-appointment")
public class TrainingAppointmentController {

    private final ITrainingAppointmentService service;

    @GetMapping
    public ResponseEntity<Page<TrainingAppointmentDto>> filter(@RequestParam (name = "gym",      required = false) String    gym,
                                                               @RequestParam (name = "training", required = false) String    training,
                                                               @RequestParam (name = "date",     required = false) LocalDate date,
                                                               @RequestParam (name = "time",     required = false) LocalTime time,
                                                               @RequestParam (name = "duration", required = false) Integer   duration,
                                                               @RequestParam (name = "status",   required = false) String    status,
                                                               @RequestHeader(name = "authorization"             ) String    token,
                                                               Pageable pageable) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.findAll(gym, training, date, time, duration, status, token, pageable), HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<TrainingAppointmentDto> create(@RequestBody @Valid                    TrainingAppointmentCreateDto createDto,
                                                         @RequestHeader(name = "authorization") String                       token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.create(createDto), HttpStatus.OK));
    }

    @PutMapping
    @CheckSecurity(role = {Role.SERVICE, Role.ADMIN, Role.MANAGER})
    public ResponseEntity<TrainingAppointmentDto> update(@RequestBody @Valid                    TrainingAppointmentUpdateDto updateDto,
                                                         @RequestHeader(name = "authorization") String                       token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.update(updateDto), HttpStatus.OK));
    }

}

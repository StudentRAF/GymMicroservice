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
import rs.raf.gym.commons.dto.client_training_appointment.ClientTrainingAppointmentCreateDto;
import rs.raf.gym.commons.dto.client_training_appointment.ClientTrainingAppointmentDto;
import rs.raf.gym.commons.dto.client_training_appointment.ClientTrainingAppointmentUpdateDto;
import rs.raf.gym.commons.exception.ExceptionUtils;
import rs.raf.gym.service.IClientTrainingAppointmentService;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@RestController
@RequestMapping("/client-training-appointment")
public class ClientTrainingAppointmentController {

    private final IClientTrainingAppointmentService service;

    @GetMapping
    public ResponseEntity<Page<ClientTrainingAppointmentDto>> filter(@RequestParam (name = "gym",      required = false) String    gym,
                                                                     @RequestParam (name = "training", required = false) String    training,
                                                                     @RequestParam (name = "date",     required = false) LocalDate date,
                                                                     @RequestParam (name = "time",     required = false) LocalTime time,
                                                                     @RequestParam (name = "client",   required = false) Long      clientId,
                                                                     @RequestParam (name = "status",   required = false) String    status,
                                                                     @RequestHeader(name = "authorization"             ) String    token,
                                                                     Pageable pageable) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.findAll(gym, training, date, time, status, clientId, pageable), HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ClientTrainingAppointmentDto> create(@RequestBody @Valid                    ClientTrainingAppointmentCreateDto createDto,
                                                               @RequestHeader(name = "authorization") String                             token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.create(createDto), HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ClientTrainingAppointmentDto> update(@RequestBody @Valid                    ClientTrainingAppointmentUpdateDto updateDto,
                                                               @RequestHeader(name = "authorization") String                             token) {
        return ExceptionUtils.handleResponse(() -> new ResponseEntity<>(service.update(updateDto), HttpStatus.OK));
    }

}

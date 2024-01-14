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

package rs.raf.gym.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.commons.exception.GymException;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ITrainingAppointmentService {

    Page<TrainingAppointmentDto> findAll(String gym, String training, LocalDate date, LocalTime time, Integer duration,
                                         String status, String token, Pageable pageable);

    TrainingAppointmentDto create(TrainingAppointmentCreateDto createDto) throws GymException;

    TrainingAppointmentDto update(TrainingAppointmentUpdateDto updateDto) throws GymException;

}

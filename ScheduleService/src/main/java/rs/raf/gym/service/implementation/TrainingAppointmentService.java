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

package rs.raf.gym.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.mapper.TrainingAppointmentMapper;
import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingAppointment;
import rs.raf.gym.repository.IAppointmentStatusRepository;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.repository.IGymTrainingRepository;
import rs.raf.gym.repository.ITrainingAppointmentRepository;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.ITrainingAppointmentService;
import rs.raf.gym.specification.TrainingAppointmentSpecification;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class TrainingAppointmentService implements ITrainingAppointmentService {

    private final ITrainingAppointmentRepository repository;
    private final IGymRepository                 gymRepository;
    private final ITrainingRepository            trainingRepository;
    private final IGymTrainingRepository         gymTrainingRepository;
    private final IAppointmentStatusRepository   statusRepository;
    private final TrainingAppointmentMapper      mapper;

    @Override
    public Page<TrainingAppointmentDto> findAll(String gym, String training, LocalDate date, LocalTime time,
                                                Integer duration, String status, Pageable pageable) {
        TrainingAppointmentSpecification specification = new TrainingAppointmentSpecification(gym, training, date, time,
                                                                                              duration, status);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toTrainingAppointmentDto);
    }

    @Override
    public TrainingAppointmentDto create(TrainingAppointmentCreateDto createDto) {
        GymTraining gymTraining = findGymTraining(createDto.getGymName(), createDto.getTrainingName());

        AppointmentStatus appointmentStatus = statusRepository.findByName(createDto.getStatusName())
                                                              .orElse(null);

        if (gymTraining == null || appointmentStatus == null) //TODO: Replace with exception
            return null;

        TrainingAppointment trainingAppointment = new TrainingAppointment();

        trainingAppointment.setGymTraining(gymTraining);
        trainingAppointment.setStatus(appointmentStatus);
        mapper.map(trainingAppointment, createDto);

        return mapper.toTrainingAppointmentDto(repository.save(trainingAppointment));
    }

    @Override
    public TrainingAppointmentDto update(TrainingAppointmentUpdateDto updateDto) {
        GymTraining gymTraining = findGymTraining(updateDto.getGymName(), updateDto.getTrainingName());

        if (gymTraining == null) //TODO: Replace with exception
            return null;

        TrainingAppointment trainingAppointment = repository.findByGymTrainingAndDateAndTime(gymTraining,
                                                                                             updateDto.getDate(),
                                                                                             updateDto.getTime())
                                                            .orElse(null);

        if (trainingAppointment == null) //TODO: Replace with exception
            return null;

        AppointmentStatus appointmentStatus = statusRepository.findByName(updateDto.getStatusName())
                                                              .orElse(null);

        trainingAppointment.setStatus(appointmentStatus);
        mapper.map(trainingAppointment, updateDto);

        return mapper.toTrainingAppointmentDto(repository.save(trainingAppointment));
    }

    private GymTraining findGymTraining(String gymName, String trainingName) {
        Gym gym = gymRepository.findByName(gymName)
                               .orElse(null);
        Training training = trainingRepository.findByName(trainingName)
                                              .orElse(null);

        if (gym == null || training == null) //TODO: Replace with exception
            return null;

        return gymTrainingRepository.findByGymAndTraining(gym, training)
                                    .orElse(null);
    }

}

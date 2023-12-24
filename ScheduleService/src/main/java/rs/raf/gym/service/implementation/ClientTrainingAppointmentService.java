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
import rs.raf.gym.dto.client_training_appointment.ClientTrainingAppointmentCreateDto;
import rs.raf.gym.dto.client_training_appointment.ClientTrainingAppointmentDto;
import rs.raf.gym.dto.client_training_appointment.ClientTrainingAppointmentUpdateDto;
import rs.raf.gym.mapper.ClientTrainingAppointmentMapper;
import rs.raf.gym.model.ClientAppointmentStatus;
import rs.raf.gym.model.ClientTrainingAppointment;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingAppointment;
import rs.raf.gym.repository.IClientAppointmentStatusRepository;
import rs.raf.gym.repository.IClientTrainingAppointmentRepository;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.repository.IGymTrainingRepository;
import rs.raf.gym.repository.ITrainingAppointmentRepository;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.IClientTrainingAppointmentService;
import rs.raf.gym.specification.ClientTrainingAppointmentSpecification;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class ClientTrainingAppointmentService implements IClientTrainingAppointmentService {

    private final IClientTrainingAppointmentRepository repository;
    private final IGymRepository                       gymRepository;
    private final ITrainingRepository                  trainingRepository;
    private final IGymTrainingRepository               gymTrainingRepository;
    private final ITrainingAppointmentRepository       trainingAppointmentRepository;
    private final IClientAppointmentStatusRepository   statusRepository;
    private final ClientTrainingAppointmentMapper      mapper;

    @Override
    public Page<ClientTrainingAppointmentDto> findAll(String gym, String training, LocalDate date, LocalTime time,
                                                      String status, Long clientId, Pageable pageable) {
        ClientTrainingAppointmentSpecification specification = new ClientTrainingAppointmentSpecification(gym,
                                                                                                          training,
                                                                                                          date,
                                                                                                          time,
                                                                                                          status,
                                                                                                          clientId);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toClientTrainingAppointmentDto);
    }

    @Override
    public ClientTrainingAppointmentDto create(ClientTrainingAppointmentCreateDto createDto) {
        TrainingAppointment trainingAppointment = findTrainingAppointment(createDto.getGymName(),
                                                                          createDto.getTrainingName(),
                                                                          createDto.getDate(),
                                                                          createDto.getTime());

        ClientAppointmentStatus status = statusRepository.findByName(createDto.getStatusName())
                                                         .orElse(null);

        if (trainingAppointment == null || status == null) //TODO: Replace with exception
            return null;

        ClientTrainingAppointment clientTrainingAppointment = new ClientTrainingAppointment();
        clientTrainingAppointment.setTrainingAppointment(trainingAppointment);
        clientTrainingAppointment.setStatus(status);
        mapper.map(clientTrainingAppointment, createDto);

        return mapper.toClientTrainingAppointmentDto(repository.save(clientTrainingAppointment));
    }

    @Override
    public ClientTrainingAppointmentDto update(ClientTrainingAppointmentUpdateDto updateDto) {
        TrainingAppointment trainingAppointment = findTrainingAppointment(updateDto.getGymName(),
                                                                          updateDto.getTrainingName(),
                                                                          updateDto.getDate(),
                                                                          updateDto.getTime());

        ClientAppointmentStatus status = statusRepository.findByName(updateDto.getStatusName())
                                                         .orElse(null);

        if (trainingAppointment == null || status == null) //TODO: Replace with exception
            return null;

        ClientTrainingAppointment clientTrainingAppointment = repository.findByTrainingAppointmentAndClientId(trainingAppointment,
                                                                                                              updateDto.getClientId())
                                                                        .orElse(null);

        if (clientTrainingAppointment == null) //TODO: Replace with exception
            return null;

        clientTrainingAppointment.setStatus(status);
        mapper.map(clientTrainingAppointment, updateDto);

        return mapper.toClientTrainingAppointmentDto(repository.save(clientTrainingAppointment));
    }

    private TrainingAppointment findTrainingAppointment(String gymName, String trainingName, LocalDate date, LocalTime time) {
        Gym gym = gymRepository.findByName(gymName)
                               .orElse(null);
        Training training = trainingRepository.findByName(trainingName)
                                              .orElse(null);

        if (gym == null || training == null) //TODO: Replace with exception
            return null;

        GymTraining gymTraining = gymTrainingRepository.findByGymAndTraining(gym, training)
                                                       .orElse(null);

        if (gymTraining == null) //TODO: Replace with exception
            return null;

        return trainingAppointmentRepository.findByGymTrainingAndDateAndTime(gymTraining, date, time)
                                            .orElse(null);
    }

}

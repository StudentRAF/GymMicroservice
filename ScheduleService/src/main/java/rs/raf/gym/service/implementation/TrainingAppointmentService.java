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

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import rs.raf.gym.commons.configuration.ServiceConfiguration;
import rs.raf.gym.commons.dto.notification.NotificationBodyDto;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.commons.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.commons.dto.user.UserAuthorizationDto;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.exception.GymException;
import rs.raf.gym.commons.message_broker.Converter;
import rs.raf.gym.commons.message_broker.MailFormat;
import rs.raf.gym.commons.model.AppointmentStatusType;
import rs.raf.gym.commons.model.ClientAppointmentStatusType;
import rs.raf.gym.commons.model.Role;
import rs.raf.gym.commons.utils.NetworkUtils;
import rs.raf.gym.exception.ExceptionType;
import rs.raf.gym.mapper.TrainingAppointmentMapper;
import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.model.ClientAppointmentStatus;
import rs.raf.gym.model.ClientTrainingAppointment;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingAppointment;
import rs.raf.gym.repository.IAppointmentStatusRepository;
import rs.raf.gym.repository.IClientAppointmentStatusRepository;
import rs.raf.gym.repository.IClientTrainingAppointmentRepository;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.repository.IGymTrainingRepository;
import rs.raf.gym.repository.ITrainingAppointmentRepository;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.ITrainingAppointmentService;
import rs.raf.gym.specification.TrainingAppointmentSpecification;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TrainingAppointmentService implements ITrainingAppointmentService {

    private final ITrainingAppointmentRepository       repository;
    private final IGymRepository                       gymRepository;
    private final ITrainingRepository                  trainingRepository;
    private final IGymTrainingRepository               gymTrainingRepository;
    private final IClientAppointmentStatusRepository   clientAppointmentStatusRepository;
    private final IClientTrainingAppointmentRepository clientTrainingAppointmentRepository;
    private final IAppointmentStatusRepository         statusRepository;
    private final TrainingAppointmentMapper            mapper;
    private final NetworkUtils                         networkUtils;
    private final ServiceConfiguration                 configuration;

    @Value("${queue.location}")
    private       String      destinationName;
    private final Converter   converter;
    private final JmsTemplate jmsTemplate;

    @Override
    public Page<TrainingAppointmentDto> findAll(String gymName, String training, LocalDate date, LocalTime time,
                                                Integer duration, String status, String token, Pageable pageable) {

        Role role   = Role.valueOf(networkUtils.request(HttpMethod.GET, "/user/role", configuration.token, new UserAuthorizationDto(token), String.class)
                                               .toUpperCase());
        Gym gym = null;

        if (Role.MANAGER.equals(role)) {
            Long managerId = networkUtils.request(HttpMethod.GET, "/user/id", configuration.token, new UserAuthorizationDto(token), Long.class);
            gym = gymRepository.findByManagerId(managerId)
                               .orElseThrow(() -> new GymException(ExceptionType.FIND_GYM_TRAINING_APPOINTMENT_NOT_FOUND_MANAGER_GYM,
                                                                   managerId.toString()));
        }

        TrainingAppointmentSpecification specification = new TrainingAppointmentSpecification(gym == null ? gymName : gym.getName(), training, date, time,
                                                                                              duration, status);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toTrainingAppointmentDto);
    }

    @Override
    public TrainingAppointmentDto create(TrainingAppointmentCreateDto createDto) throws GymException {
        GymTraining gymTraining = findGymTraining(createDto.getGymName(), createDto.getTrainingName(), false);

        AppointmentStatus appointmentStatus = statusRepository.findByName(AppointmentStatusType.PENDING.getName())
                                                              .orElseThrow(() -> new GymException(ExceptionType.CREATE_TRAINING_APPOINTMENT_NOT_FOUND_APPOINTMENT_STATUS,
                                                                                                  AppointmentStatusType.PENDING.getName()));

        TrainingAppointment trainingAppointment = new TrainingAppointment();

        trainingAppointment.setGymTraining(gymTraining);
        trainingAppointment.setStatus(appointmentStatus);
        mapper.map(trainingAppointment, createDto);

        return mapper.toTrainingAppointmentDto(repository.save(trainingAppointment));
    }

    @Override
    public TrainingAppointmentDto update(TrainingAppointmentUpdateDto updateDto) throws GymException {
        GymTraining gymTraining = findGymTraining(updateDto.getGymName(), updateDto.getTrainingName(), true);

        TrainingAppointment trainingAppointment = repository.findByGymTrainingAndDateAndTime(gymTraining,
                                                                                             updateDto.getDate(),
                                                                                             updateDto.getTime())
                                                            .orElseThrow(() -> new GymException(ExceptionType.UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING_APPOINTMENT,
                                                                                                updateDto.getGymName(), updateDto.getTrainingName(),
                                                                                                updateDto.getDate().toString(), updateDto.getTime().toString()));

        AppointmentStatus appointmentStatus = statusRepository.findByName(updateDto.getStatusName())
                                                              .orElseThrow(() -> new GymException(ExceptionType.UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_APPOINTMENT_STATUS,
                                                                                                  updateDto.getStatusName()));

        if (AppointmentStatusType.CANCELED.equals(AppointmentStatusType.findStatus(updateDto.getStatusName())) &&
            AppointmentStatusType.PENDING.equals(AppointmentStatusType.findStatus(trainingAppointment.getStatus().getName()))) {
            clientTrainingAppointmentRepository.findAllByTrainingAppointment(trainingAppointment)
                                               .forEach(this::cancelTraining);

            trainingAppointment.setStatus(appointmentStatus);
        }

        mapper.map(trainingAppointment, updateDto);



        return mapper.toTrainingAppointmentDto(repository.save(trainingAppointment));
    }

    private void cancelTraining(ClientTrainingAppointment clientTrainingAppointment) {
        if (ClientAppointmentStatusType.CANCELED.equals(ClientAppointmentStatusType.findStatus(clientTrainingAppointment.getStatus().getName())))
            return;

        ClientAppointmentStatus appointmentStatus = clientAppointmentStatusRepository.findByName(ClientAppointmentStatusType.CANCELED.getName())
                                                                                     .orElse(null);
        clientTrainingAppointment.setStatus(appointmentStatus);
        clientTrainingAppointmentRepository.save(clientTrainingAppointment);

        UserDto userDto = networkUtils.request(HttpMethod.GET, "/user/" + clientTrainingAppointment.getClientId(), configuration.token, UserDto.class);

        NotificationBodyDto notificationBodyDto = new NotificationBodyDto("Canceled training", userDto, clientTrainingAppointment.getClientId(),
                                                                          clientTrainingAppointment.getTrainingAppointment().getDate(),
                                                                          clientTrainingAppointment.getTrainingAppointment().getGymTraining().getTraining().getName(),
                                                                          MailFormat.CANCELED_TRAINING);
        jmsTemplate.convertAndSend(destinationName, converter.serialize(notificationBodyDto));
    }

    private GymTraining findGymTraining(String gymName, String trainingName, boolean update) throws GymException {
        Gym gym = gymRepository.findByName(gymName)
                               .orElseThrow(() -> new GymException(update ? ExceptionType.UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM
                                                                          : ExceptionType.CREATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM,
                                                                   gymName));

        Training training = trainingRepository.findByName(trainingName)
                                              .orElseThrow(() -> new GymException(update ? ExceptionType.UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING
                                                                                         : ExceptionType.CREATE_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING,
                                                                                  gymName));

        return gymTrainingRepository.findByGymAndTraining(gym, training)
                                    .orElseThrow(() -> new GymException(update ? ExceptionType.UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING
                                                                               : ExceptionType.CREATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING,
                                                                        gymName, trainingName));
    }

}

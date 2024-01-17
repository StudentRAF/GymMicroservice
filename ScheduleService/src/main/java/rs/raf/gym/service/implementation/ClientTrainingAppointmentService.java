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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rs.raf.gym.commons.configuration.ServiceConfiguration;
import rs.raf.gym.commons.dto.client_training_appointment.ClientTrainingAppointmentCreateDto;
import rs.raf.gym.commons.dto.client_training_appointment.ClientTrainingAppointmentDto;
import rs.raf.gym.commons.dto.client_training_appointment.ClientTrainingAppointmentUpdateDto;
import rs.raf.gym.commons.dto.notification.NotificationBodyDto;
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
import rs.raf.gym.mapper.ClientTrainingAppointmentMapper;
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
import rs.raf.gym.service.IClientTrainingAppointmentService;
import rs.raf.gym.specification.ClientTrainingAppointmentSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientTrainingAppointmentService implements IClientTrainingAppointmentService {

    private final IClientTrainingAppointmentRepository repository;
    private final IGymRepository                       gymRepository;
    private final ITrainingRepository                  trainingRepository;
    private final IGymTrainingRepository               gymTrainingRepository;
    private final ITrainingAppointmentRepository       trainingAppointmentRepository;
    private final IClientAppointmentStatusRepository   statusRepository;
    private final IAppointmentStatusRepository         appointmentStatusRepository;
    private final ClientTrainingAppointmentMapper      mapper;
    private final NetworkUtils         networkUtils;
    private final ServiceConfiguration configuration;

    @Value("${queue.location}")
    private String destinationName;
    private final Converter converter;
    private final JmsTemplate jmsTemplate;

    @Override
    public Page<ClientTrainingAppointmentDto> findAll(String gym, String training, LocalDate date, LocalTime time,
                                                      String status, String token, Pageable pageable) {
        Long userId = null;
        Role role   = Role.valueOf(networkUtils.request(HttpMethod.GET, "/user/role", configuration.token, new UserAuthorizationDto(token), String.class)
                                                   .toUpperCase());

        if (Role.CLIENT.equals(role))
            userId = networkUtils.request(HttpMethod.GET, "/user/id", configuration.token, new UserAuthorizationDto(token), Long.class);

        ClientTrainingAppointmentSpecification specification = new ClientTrainingAppointmentSpecification(gym,
                                                                                                          training,
                                                                                                          date,
                                                                                                          time,
                                                                                                          status,
                                                                                                          Role.CLIENT.equals(role) ? userId : null);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toClientTrainingAppointmentDto);
    }

    @Override
    public ClientTrainingAppointmentDto create(ClientTrainingAppointmentCreateDto createDto, String token) throws GymException {
        Long clientId = networkUtils.request(HttpMethod.GET, "/user/id", configuration.token, new UserAuthorizationDto(token), Long.class);

        TrainingAppointment trainingAppointment = findTrainingAppointment(createDto.getGymName(),
                                                                          createDto.getTrainingName(),
                                                                          createDto.getDate(),
                                                                          createDto.getTime(),
                                                                          false);

        ClientAppointmentStatus status = statusRepository.findByName(ClientAppointmentStatusType.REQUESTED.getName())
                                                         .orElseThrow(() -> new GymException(ExceptionType.CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_CLIENT_APPOINTMENT_STATUS,
                                                                                             ClientAppointmentStatusType.REQUESTED.getName()));

        ClientTrainingAppointment clientTrainingAppointment = new ClientTrainingAppointment();

        clientTrainingAppointment.setClientId(clientId);
        clientTrainingAppointment.setTrainingAppointment(trainingAppointment);
        clientTrainingAppointment.setStatus(status);
        mapper.map(clientTrainingAppointment, createDto);

        return mapper.toClientTrainingAppointmentDto(repository.save(clientTrainingAppointment));
    }

    @Override
    public ClientTrainingAppointmentDto update(ClientTrainingAppointmentUpdateDto updateDto, String token) throws GymException {
        Long clientId = networkUtils.request(HttpMethod.GET, "/user/id", configuration.token, new UserAuthorizationDto(token), Long.class);

        TrainingAppointment trainingAppointment = findTrainingAppointment(updateDto.getGymName(),
                                                                          updateDto.getTrainingName(),
                                                                          updateDto.getDate(),
                                                                          updateDto.getTime(),
                                                                          true);

        ClientAppointmentStatus status = statusRepository.findByName(updateDto.getStatusName())
                                                         .orElseThrow(() -> new GymException(ExceptionType.UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_CLIENT_APPOINTMENT_STATUS,
                                                                                             updateDto.getStatusName()));

        ClientTrainingAppointment clientTrainingAppointment = repository.findByTrainingAppointmentAndClientId(trainingAppointment, clientId)
                                                                        .orElseThrow(() -> new GymException(ExceptionType.UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_CLIENT_TRAINING_APPOINTMENT,
                                                                                                            updateDto.getGymName(), updateDto.getTrainingName(), updateDto.getDate().toString(),
                                                                                                            updateDto.getTime().toString(), clientId.toString()));

        clientTrainingAppointment.setStatus(status);
        mapper.map(clientTrainingAppointment, updateDto);

        return mapper.toClientTrainingAppointmentDto(repository.save(clientTrainingAppointment));
    }

    private TrainingAppointment findTrainingAppointment(String gymName, String trainingName, LocalDate date, LocalTime time, boolean update) throws GymException {
        Gym gym = gymRepository.findByName(gymName)
                               .orElseThrow(() -> new GymException(update ? ExceptionType.UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM
                                                                          : ExceptionType.CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM,
                                                                   gymName));

        Training training = trainingRepository.findByName(trainingName)
                                              .orElseThrow(() -> new GymException(update ? ExceptionType.UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING
                                                                                         : ExceptionType.CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING,
                                                                                  trainingName));

        GymTraining gymTraining = gymTrainingRepository.findByGymAndTraining(gym, training)
                                                       .orElseThrow(() -> new GymException(update ? ExceptionType.UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING
                                                                                                  : ExceptionType.CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING,
                                                                                           gymName, trainingName));

        return trainingAppointmentRepository.findByGymTrainingAndDateAndTime(gymTraining, date, time)
                                            .orElseThrow(() -> new GymException(update ? ExceptionType.UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING_APPOINTMENT
                                                                                       : ExceptionType.CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING_APPOINTMENT,
                                                                                gymName, trainingName, date.toString(), time.toString()));
    }

    @Scheduled(cron = "0 0-23 * * * ?")
    private void findLastFullDayBeforeTraining() {
        List<ClientTrainingAppointment> clientTrainings =  repository.findReadyDates();

        List<ClientTrainingAppointment> send = new ArrayList<>();
        LocalDateTime localDateTime;
        for (ClientTrainingAppointment clientTraining : clientTrainings) {
            localDateTime = LocalDateTime.of(clientTraining.getTrainingAppointment().getDate(), clientTraining.getTrainingAppointment().getTime());

            //less than 24h
            if (LocalDateTime.now().isBefore(localDateTime) && LocalDateTime.now().plusDays(1).isAfter(localDateTime))
                send.add(clientTraining);
        }

        System.out.println(send);

        AppointmentStatus appointmentStatus = appointmentStatusRepository.findByName(AppointmentStatusType.HELD.getName()).orElse(null);

        if (appointmentStatus == null)
            return;

        for (ClientTrainingAppointment clientTraining : send) {
            clientTraining.getTrainingAppointment().setStatus(appointmentStatus);
            trainingAppointmentRepository.save(clientTraining.getTrainingAppointment());
            UserDto userDto = networkUtils.request(HttpMethod.GET, "/user/" + clientTraining.getClientId(), configuration.token, UserDto.class);
            NotificationBodyDto notificationBodyDto = new NotificationBodyDto("Reminder", userDto,  clientTraining.getClientId(),
                            clientTraining.getTrainingAppointment().getDate(), clientTraining.getTrainingAppointment().getGymTraining().getTraining().getName(), MailFormat.REMINDER);
            jmsTemplate.convertAndSend(destinationName, converter.serialize(notificationBodyDto));
        }
    }

}

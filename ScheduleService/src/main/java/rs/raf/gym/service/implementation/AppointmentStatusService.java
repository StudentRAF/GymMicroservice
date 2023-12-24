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
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusCreateDto;
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusDto;
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusUpdateDto;
import rs.raf.gym.mapper.AppointmentStatusMapper;
import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.repository.IAppointmentStatusRepository;
import rs.raf.gym.service.IAppointmentStatusService;
import rs.raf.gym.specification.AppointmentStatusSpecification;

@Service
@AllArgsConstructor
public class AppointmentStatusService implements IAppointmentStatusService {

    private final IAppointmentStatusRepository repository;
    private final AppointmentStatusMapper      mapper;

    @Override
    public Page<AppointmentStatusDto> findAll(String name, Pageable pageable) {
        AppointmentStatusSpecification specification = new AppointmentStatusSpecification(name);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toAppointmentStatusDto);
    }

    @Override
    public AppointmentStatusDto create(AppointmentStatusCreateDto createDto) {
        AppointmentStatus appointmentStatus = new AppointmentStatus();

        mapper.map(appointmentStatus, createDto);

        return mapper.toAppointmentStatusDto(repository.save(appointmentStatus));
    }

    @Override
    public AppointmentStatusDto update(AppointmentStatusUpdateDto updateDto) {
        AppointmentStatus appointmentStatus = repository.findByName(updateDto.getOldName())
                                                        .orElse(null);

        if (appointmentStatus == null) //TODO: Replace with exception
            return null;

        mapper.map(appointmentStatus, updateDto);

        return mapper.toAppointmentStatusDto(repository.save(appointmentStatus));
    }

}

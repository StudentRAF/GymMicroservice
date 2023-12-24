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

package rs.raf.gym.mapper;

import org.springframework.stereotype.Component;
import rs.raf.gym.dto.client_appointment_status.ClientAppointmentStatusCreateDto;
import rs.raf.gym.dto.client_appointment_status.ClientAppointmentStatusDto;
import rs.raf.gym.dto.client_appointment_status.ClientAppointmentStatusUpdateDto;
import rs.raf.gym.model.ClientAppointmentStatus;

@Component
public class ClientAppointmentStatusMapper {

    /**
     * Maps ClientAppointmentStatus to ClientAppointmentStatusDto object.
     * @param appointmentStatus appointment status
     * @return ClientAppointmentStatusDto object
     */
    public ClientAppointmentStatusDto toAppointmentStatusDto(ClientAppointmentStatus appointmentStatus) {
        return new ClientAppointmentStatusDto(appointmentStatus.getName());
    }

    /**
     * Maps ClientAppointmentStatusCreateDto to existing ClientAppointmentStatus object.
     * @param appointmentStatus appointment status
     * @param createDto create dto
     * @return ClientAppointmentStatus object
     */
    public ClientAppointmentStatus map(ClientAppointmentStatus appointmentStatus, ClientAppointmentStatusCreateDto createDto) {
        appointmentStatus.setName(createDto.getName());

        return appointmentStatus;
    }

    /**
     * Maps ClientAppointmentStatusUpdateDto to existing ClientAppointmentStatus object.
     * @param appointmentStatus appointment status
     * @param updateDto update dto
     * @return ClientAppointmentStatus object
     */
    public ClientAppointmentStatus map(ClientAppointmentStatus appointmentStatus, ClientAppointmentStatusUpdateDto updateDto) {
        appointmentStatus.setName(updateDto.getName());

        return appointmentStatus;
    }

}

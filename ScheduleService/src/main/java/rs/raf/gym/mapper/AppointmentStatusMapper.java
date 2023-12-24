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
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusCreateDto;
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusDto;
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusUpdateDto;
import rs.raf.gym.model.AppointmentStatus;

@Component
public class AppointmentStatusMapper {

    /**
     * Maps AppointmentStatus to AppointmentStatusDto object.
     * @param appointmentStatus appointment status
     * @return AppointmentStatusDto object
     */
    public AppointmentStatusDto toAppointmentStatusDto(AppointmentStatus appointmentStatus) {
        return new AppointmentStatusDto(appointmentStatus.getName());
    }

    /**
     * Maps AppointmentStatusCreateDto to existing AppointmentStatus object.
     * @param appointmentStatus appointment status
     * @param createDto create dto
     * @return AppointmentStatus object
     */
    public AppointmentStatus map(AppointmentStatus appointmentStatus, AppointmentStatusCreateDto createDto) {
        appointmentStatus.setName(createDto.getName());

        return appointmentStatus;
    }

    /**
     * Maps AppointmentStatusUpdateDto to existing AppointmentStatus object.
     * @param appointmentStatus appointment status
     * @param updateDto update dto
     * @return AppointmentStatus object
     */
    public AppointmentStatus map(AppointmentStatus appointmentStatus, AppointmentStatusUpdateDto updateDto) {
        appointmentStatus.setName(updateDto.getName());

        return appointmentStatus;
    }

}

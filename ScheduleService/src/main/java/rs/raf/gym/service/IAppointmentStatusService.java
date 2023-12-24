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
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusCreateDto;
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusDto;
import rs.raf.gym.commons.dto.appointment_status.AppointmentStatusUpdateDto;

public interface IAppointmentStatusService {

    Page<AppointmentStatusDto> findAll(String name, Pageable pageable);

    AppointmentStatusDto create(AppointmentStatusCreateDto createDto);

    AppointmentStatusDto update(AppointmentStatusUpdateDto updateDto);

}

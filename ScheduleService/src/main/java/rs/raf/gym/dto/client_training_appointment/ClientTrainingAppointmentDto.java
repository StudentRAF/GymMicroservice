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

package rs.raf.gym.dto.client_training_appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.gym.dto.client_appointment_status.ClientAppointmentStatusDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientTrainingAppointmentDto {

    private TrainingAppointmentDto trainingAppointment;

    private Long clientId;

    private ClientAppointmentStatusDto status;

}

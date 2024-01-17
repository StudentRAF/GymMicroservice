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

package rs.raf.gym.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.raf.gym.commons.dto.client_training_appointment.ClientTrainingAppointmentDto;
import rs.raf.gym.commons.service.JpaSpecificationRepository;
import rs.raf.gym.model.ClientTrainingAppointment;
import rs.raf.gym.model.TrainingAppointment;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClientTrainingAppointmentRepository extends JpaSpecificationRepository<ClientTrainingAppointment, Long> {

    Optional<ClientTrainingAppointment> findByTrainingAppointmentAndClientId(TrainingAppointment appointment, Long clientId);

    List<ClientTrainingAppointment> findAllByTrainingAppointment(TrainingAppointment trainingAppointment);
    @Query("SELECT c FROM ClientTrainingAppointment c INNER JOIN TrainingAppointment t ON c.trainingAppointment.id = t.id WHERE t.status.name = 'Pending'")
    List<ClientTrainingAppointment> findReadyDates();

}

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

package rs.raf.gym.specification;

import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.model.ClientAppointmentStatus;
import rs.raf.gym.model.ClientTrainingAppointment;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingAppointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class ClientTrainingAppointmentSpecification extends BaseSpecification<ClientTrainingAppointment> {

    public ClientTrainingAppointmentSpecification(String gym, String training, LocalDate date, LocalTime time,
                                                  String status, Long clientId) {
        addGymSpecification(gym);
        addTrainingSpecification(training);
        addDateSpecification(date);
        addTimeSpecification(time);
        addStatusSpecification(status);
        addClientSpecification(clientId);
    }

    private void addGymSpecification(String gym) {
        if (gym == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(ClientTrainingAppointment.trainingAppointment())
                                                                                       .join(TrainingAppointment.gymTraining())
                                                                                       .join(GymTraining.gym())
                                                                                       .get(AppointmentStatus.name()),
                                                                                   gym));
    }

    private void addTrainingSpecification(String training) {
        if (training == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(ClientTrainingAppointment.trainingAppointment())
                                                                                       .join(TrainingAppointment.gymTraining())
                                                                                       .join(GymTraining.training())
                                                                                       .get(Training.name()),
                                                                                   training));
    }

    private void addDateSpecification(LocalDate date) {
        if (date == null)
            return;
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(ClientTrainingAppointment.trainingAppointment())
                                                                                       .get(TrainingAppointment.date()),
                                                                                   date));
    }

    private void addTimeSpecification(LocalTime time) {
        if (time == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(ClientTrainingAppointment.trainingAppointment())
                                                                                       .get(TrainingAppointment.time()),
                                                                                   time));
    }

    private void addStatusSpecification(String status) {
        if (status == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(ClientTrainingAppointment.status())
                                                                                       .get(ClientAppointmentStatus.name()),
                                                                                   status));
    }

    public void addClientSpecification(Long clientId) {
        if (clientId == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ClientTrainingAppointment.client()),
                                                                                   clientId));
    }

}

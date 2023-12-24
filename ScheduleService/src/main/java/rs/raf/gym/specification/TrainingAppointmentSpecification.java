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
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingAppointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingAppointmentSpecification extends BaseSpecification<TrainingAppointment> {

    public TrainingAppointmentSpecification(String gym, String training, LocalDate date, LocalTime time,
                                            Integer duration, String status) {
        addGymSpecification(gym);
        addTrainingSpecification(training);
        addDateSpecification(date);
        addTimeSpecification(time);
        addDurationSpecification(duration);
        addStatusSpecification(status);
    }

    private void addGymSpecification(String gym) {
        if (gym == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.gymTraining())
                                                                                       .join(GymTraining.gym())
                                                                                       .get(Gym.name()),
                                                                                   gym));
    }

    private void addTrainingSpecification(String training) {
        if (training == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.gymTraining())
                                                                                       .join(GymTraining.training())
                                                                                       .get(Training.name()),
                                                                                   training));
    }

    private void addDateSpecification(LocalDate date) {
        if (date == null)
            return;
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TrainingAppointment.date()),
                                                                                   date));
    }

    private void addTimeSpecification(LocalTime time) {
        if (time == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TrainingAppointment.time()),
                                                                                   time));
    }

    private void addDurationSpecification(Integer duration) {
        if (duration == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(TrainingAppointment.duration()),
                                                                                   duration));
    }

    private void addStatusSpecification(String status) {
        if (status == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(TrainingAppointment.status())
                                                                                       .get(AppointmentStatus.name()),
                                                                                   status));
    }

}

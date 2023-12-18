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

package rs.raf.gym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import rs.raf.gym.model.composite.TrainingAppointmentComposite;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@Table(name = "training_appointment")
@IdClass(TrainingAppointmentComposite.class)
public class TrainingAppointment {

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "gym_id"),
            @JoinColumn(name = "training_id")
    })
    private GymTraining gymTraining;

    @Id
    private LocalDate date;

    @Id
    private LocalTime time;

    @NotNull
    @Positive
    private Integer duration;

    @NotNull
    @ManyToOne
    private AppointmentStatus status;

}

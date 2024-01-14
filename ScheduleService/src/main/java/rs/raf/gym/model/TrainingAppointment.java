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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_appointment", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueGymTrainingForDateTime", columnNames = {
                "gym_training_id",
                "date",
                "time"
        })
})
public class TrainingAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gym_training_id", nullable = false)
    private GymTraining gymTraining;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private AppointmentStatus status;

    @Override
    public boolean equals(Object object) {
        if (object instanceof TrainingAppointment appointment)
            return Objects.equals(appointment.getId(), id);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gymTraining, date, time, duration, status);
    }

    /**
     * Returns the gym training field identifier.
     * @return gym training identifier
     */
    public static String gymTraining() {
        return "gymTraining";
    }

    /**
     * Returns the date field identifier.
     * @return date identifier
     */
    public static String date() {
        return "date";
    }

    /**
     * Returns the time field identifier.
     * @return time identifier
     */
    public static String time() {
        return "time";
    }

    /**
     * Returns the appointment duration field identifier.
     * @return appointment duration identifier
     */
    public static String duration() {
        return "duration";
    }

    /**
     * Returns the appointment status field identifier.
     * @return appointment status identifier
     */
    public static String status() {
        return "status";
    }

}

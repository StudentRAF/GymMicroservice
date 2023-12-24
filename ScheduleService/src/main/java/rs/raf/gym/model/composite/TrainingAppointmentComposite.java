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

package rs.raf.gym.model.composite;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TrainingAppointmentComposite implements Serializable {

//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "gym_id"),
//            @JoinColumn(name = "training_id")
//    })
    @Embedded
    @JoinColumns({
            @JoinColumn(name = "gym_id"),
            @JoinColumn(name = "training_id")
    })
    private GymTrainingComposite gymTraining;

    @JoinColumn(name = "date")
    private LocalDate date;

    @JoinColumn(name = "time")
    private LocalTime time;

    @Override
    public boolean equals(Object object) {
        if (object instanceof TrainingAppointmentComposite composite)
            return Objects.equals(composite.getDate(), date)      &&
                   Objects.equals(composite.getTime(), time)      &&
                   Objects.equals(composite.getGymTraining(), gymTraining);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gymTraining, date, time);
    }

    /**
     * Returns the gym training field identifier.
     * @return gym training identifier
     */
    public static String gymTraining() {
        return "gymTraining";
    }

    /**
     * Returns the appointment date field identifier.
     * @return appointment date identifier
     */
    public static String date() {
        return "date";
    }

    /**
     * Returns the appointment time field identifier.
     * @return appointment time identifier
     */
    public static String time() {
        return "time";
    }

}

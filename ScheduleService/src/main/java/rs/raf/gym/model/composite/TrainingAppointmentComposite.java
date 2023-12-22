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

import lombok.Getter;
import lombok.Setter;
import rs.raf.gym.model.GymTraining;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Setter
@Getter
public class TrainingAppointmentComposite implements Serializable {

    private GymTraining training;

    private LocalDate date;

    private LocalTime time;

    @Override
    public boolean equals(Object object) {
        if (object instanceof TrainingAppointmentComposite composite)
            return Objects.equals(composite.getDate(), date)      &&
                   Objects.equals(composite.getTime(), time)      &&
                   Objects.equals(composite.getTraining(), training);

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

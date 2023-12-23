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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.Training;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GymTrainingComposite implements Serializable {

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Override
    public boolean equals(Object object) {
        if (object instanceof GymTrainingComposite composite)
            return Objects.equals(composite.getGym(),      gym)   &&
                   Objects.equals(composite.getTraining(), training);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gym, training);
    }

    /**
     * Returns the gym field identifier.
     * @return gym identifier
     */
    public static String gym() {
        return "gym";
    }

    /**
     * Returns the training field identifier.
     * @return training identifier
     */
    public static String training() {
        return "training";
    }

}


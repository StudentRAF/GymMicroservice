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
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rs.raf.gym.model.composite.GymTrainingComposite;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "gym_training")
public class GymTraining {

    @EmbeddedId
    private GymTrainingComposite gymTraining;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer maxParticipants;

    @Column(nullable = false)
    private Integer minParticipants;

    @Override
    public boolean equals(Object object) {
        if (object instanceof GymTraining instance)
            return Objects.equals(instance.getGymTraining(), gymTraining);

        return false;
    }

    /**
     * Returns the composite (GymTrainingComposite) field identifier.
     * @return composite identifier
     */
    public static String composite() {
        return "gymTraining";
    }

    /**
     * Returns the price field identifier.
     * @return price identifier
     */
    public static String price() {
        return "price";
    }

    /**
     * Returns the max participants field identifier.
     * @return max participants identifier
     */
    public static String maxParticipants() {
        return "maxParticipants";
    }

    /**
     * Returns the min participants field identifier.
     * @return min participants identifier
     */
    public static String minParticipants() {
        return "minParticipants";
    }

}

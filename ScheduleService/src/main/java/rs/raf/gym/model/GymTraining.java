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
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rs.raf.gym.model.composite.GymTrainingComposite;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "gym_training")
@IdClass(GymTrainingComposite.class)
public class GymTraining {

    @Id
    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Id
    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Column(nullable = false)
    private Double duration;

    @Column(nullable = false)
    private Integer maxParticipants;

    @Column(nullable = false)
    private Integer minParticipants;

    @Override
    public boolean equals(Object object) {
        if (object instanceof GymTraining instance)
            return Objects.equals(instance.getGym(),      gym)   &&
                   Objects.equals(instance.getTraining(), training);

        return false;
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

    /**
     * Returns the duration field identifier.
     * @return duration identifier
     */
    public static String duration() {
        return "duration";
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

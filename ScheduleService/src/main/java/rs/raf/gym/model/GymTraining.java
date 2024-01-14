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

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gym_training", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueTrainingPerGym", columnNames = {
                "gym_id",
                "training_id"
        })
})
public class GymTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @Column(name = "min_participants", nullable = false)
    private Integer minParticipants;

    @Override
    public boolean equals(Object object) {
        if (object instanceof GymTraining instance)
            return Objects.equals(instance.getId(), id);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gym, training, price, maxParticipants, minParticipants);
    }

    /**
     * Returns the gym field identifier.
     * @return training identifier
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

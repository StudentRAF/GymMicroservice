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
@Table(name = "user_training", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueClientTraining", columnNames = {
                "client_id",
                "training_id"
        })
})
public class UserTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(name = "training_count", nullable = false)
    private Integer count;

    @Override
    public boolean equals(Object object) {
        if (object instanceof UserTraining userTraining)
            return Objects.equals(userTraining.getId(), id);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, training, count);
    }

    /**
     * Returns the client field identifier.
     * @return client identifier
     */
    public static String client() {
        return "clientId";
    }

    /**
     * Returns the training field identifier.
     * @return training identifier
     */
    public static String training() {
        return "training";
    }

    /**
     * Returns the training count field identifier.
     * @return count identifier
     */
    public static String count() {
        return "count";
    }

}

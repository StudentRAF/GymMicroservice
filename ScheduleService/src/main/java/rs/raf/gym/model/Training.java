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
@Table(name = "training", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueTrainingName", columnNames = "name")
})
public class Training {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_type", nullable = false)
    private TrainingType type;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "loyalty")
    private Integer loyalty;

    @Override
    public boolean equals(Object object) {
        if (object instanceof Training training)
            return Objects.equals(training.getId(), id);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, description, loyalty);
    }

    /**
     * Returns the training name field identifier.
     * @return name identifier
     */
    public static String name() {
        return "name";
    }

    /**
     * Returns the training type identifier.
     * @return training type identifier
     */
    public static String trainingType() {
        return "type";
    }

    /**
     * Returns the description field identifier.
     * @return description identifier
     */
    public static String description() {
        return "description";
    }

    /**
     * Returns the loyalty field identifier.
     * @return loyalty identifier
     */
    public static String loyalty() {
        return "loyalty";
    }

}

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
@Table(name = "gym", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueGymName", columnNames = "name"),
        @UniqueConstraint(name = "UniqueStatusName", columnNames = "manager_id")
})
public class Gym {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "number_of_trainers", nullable = false)
    private Integer trainers;

    @Override
    public boolean equals(Object object) {
        if (object instanceof Gym gym)
            return Objects.equals(gym.getId(), id);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, managerId, trainers);
    }

    /**
     * Returns the gym name field identifier.
     * @return name identifier
     */
    public static String name() {
        return "name";
    }

    /**
     * Returns the gym description field identifier.
     * @return description identifier
     */
    public static String description() {
        return "type";
    }

    /**
     * Returns the gym manager field identifier.
     * @return description identifier
     */
    public static String manager() {
        return "managerId";
    }

    /**
     * Returns the gym trainers field identifier.
     * @return description identifier
     */
    public static String trainers() {
        return "trainers";
    }

}

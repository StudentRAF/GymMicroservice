/*
 * Copyright (C) 2024. Lazar Dobrota and Nemanja Radovanovic
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
@Table(name = "notification_type", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 998)
    private String name;

    // Gmail has a max size limit of the HTML 102kB
    @Column(name = "pattern", nullable = false, length = 100 * 1024)
    private String pattern;

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;

        if (object instanceof NotificationType notificationType)
            return Objects.equals(notificationType.getId(), id);

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static String id() {
        return "id";
    }

    public static String subject() {
        return "subject";
    }

    public static String pattern() {
        return "pattern";
    }

}

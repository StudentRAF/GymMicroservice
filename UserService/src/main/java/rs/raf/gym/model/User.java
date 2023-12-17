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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private UserRole userRole;

    @NotNull
    private String firstname;

    @NotNull
    @Size(min = 1, max = 30)
    private String lastname;

    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = 8, max = 50)
    private String password;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    private LocalDate dateOfBirth;

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long membershipId; //TODO should automatically add numbers

    @NotNull
    private String gym; //foreign key

    //CurrentTimestamp automatically generates current date (LocalDate.now())
    @NotNull
    @CurrentTimestamp
    private LocalDate recruitmentDate;

    @NotNull
    private boolean access;
    @NotNull
    private boolean activated;
}

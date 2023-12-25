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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserRole userRole;

    @Column(nullable = false, length = 30)
    private String firstname;

    @Column(nullable = false, length = 30)
    private String lastname;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    private LocalDate dateOfBirth;

    private UUID membershipId;

    private Long gymId;

    private LocalDate recruitmentDate;

    @Column(nullable = false)
    private boolean access;

    @Column(nullable = false)
    private boolean activated;

    //When insert in database is called
    @PrePersist
    protected void onInsert() {
        if (Roles.CLIENT.isEqual(this.userRole))
            this.setMembershipId(UUID.randomUUID());
        else if (Roles.MANAGER.isEqual(this.userRole))
            this.setRecruitmentDate(LocalDate.now());

        this.access = true;
        this.activated = true;
    }


    public static String userRole() {
        return "userRole";
    }

    public static String firstname() {
        return "firstname";
    }

    public static String lastname() {
        return "lastname";
    }

    public static String username() {
        return "username";
    }

    public static String password() {
        return "password";
    }

    public static String email() {
        return "email";
    }

    public static String dateOfBirth() {
        return "dateOfBirth";
    }

    public static String membership() {
        return "membershipId";
    }

    public static String gym() {
        return "gymId";
    }

    public static String access() {
        return "access";
    }

    public static String activated() {
        return "activated";
    }

}

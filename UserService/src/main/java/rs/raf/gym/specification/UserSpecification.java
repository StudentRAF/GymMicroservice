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

package rs.raf.gym.specification;

import rs.raf.gym.commons.specification.BaseSpecification;
import rs.raf.gym.model.User;
import rs.raf.gym.model.UserRole;

public class UserSpecification extends BaseSpecification<User> {

    public UserSpecification(String userRole, String firstname, String lastname, String username) {
        addRoleSpecification(userRole);
        addFirstnameSpecification(firstname);
        addLastnameSpecification(lastname);
        addUsernameSpecification(username);
    }

    private void addRoleSpecification(String userRole) {
        if (userRole == null)
            return;

        specifications.add(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(User.userRole())
                                                                                        .get(UserRole.name()),
                                                                                    userRole)));
    }

    private void addFirstnameSpecification(String firstname) {
        if (firstname == null)
            return;

        specifications.add(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.firstname()),
                                                                                    firstname)));
    }

    private void addLastnameSpecification(String lastname) {
        if (lastname == null)
            return;

        specifications.add(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.lastname()),
                                                                                    lastname)));
    }

    private void addUsernameSpecification(String username) {
        if (username == null)
            return;

        specifications.add(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.username()),
                                                                                    username)));
    }

}

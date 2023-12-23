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

import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import rs.raf.gym.model.Gym;

@Getter
public class GymSpecification extends BaseSpecification<Gym> {

    private GymSpecification(String name, Integer manager) {
        if (name != null)
            addNameSpecification(name);

        if (manager != null)
            addManagerSpecification(manager);
    }

    private void addNameSpecification(String name) {
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Gym.name()), name));
    }

    private void addManagerSpecification(Integer manager) {
        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Gym.manager()), manager));
    }

    public static Specification<Gym> get(String name, Integer manager) {
        return BaseSpecification.get(new GymSpecification(name, manager));
    }

}

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
import rs.raf.gym.model.Training;

public class TrainingSpecification extends BaseSpecification<Training> {

    public TrainingSpecification(String name, String type, Integer loyalty) {
        addNameSpecification(name);
        addTypeSpecification(type);
        addLoyaltySpecification(loyalty);
    }

    private void addNameSpecification(String name) {
        if (name == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Training.name()), name));
    }

    private void addTypeSpecification(String type) {
        if (type == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(Training.trainingType())
                                                                                       .get(Training.name()),
                                                                                   type));
    }

    private void addLoyaltySpecification(Integer loyalty) {
        if (loyalty == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Training.loyalty()),
                                                                                   loyalty));
    }

}

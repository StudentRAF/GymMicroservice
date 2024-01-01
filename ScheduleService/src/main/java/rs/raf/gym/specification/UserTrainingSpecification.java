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
import rs.raf.gym.model.UserTraining;

public class UserTrainingSpecification extends BaseSpecification<UserTraining> {

    public UserTrainingSpecification(String training, Long clientId) {
        addTrainingSpecification(training);
        addClientSpecification(clientId);
    }

    private void addTrainingSpecification(String training) {
        if (training == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(UserTraining.training())
                                                                                       .get(Training.name()),
                                                                                   training));
    }

    private void addClientSpecification(Long clientId) {
        if (clientId == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(UserTraining.client()),
                                                                                   clientId));
    }

}

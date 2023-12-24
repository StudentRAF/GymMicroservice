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

import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;

public class GymTrainingSpecification extends BaseSpecification<GymTraining> {

    public GymTrainingSpecification(String gym, String training, Double price, Integer minParticipants,
                                    Integer maxParticipants) {
        addGymSpecification(gym);
        addTrainingSpecification(training);
        addPriceSpecification(price);
        addMinParticipantsSpecification(minParticipants);
        addMaxParticipantsSpecification(maxParticipants);
    }

    private void addGymSpecification(String gym) {
        if (gym == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(GymTraining.gym())
                                                                                       .get(Gym.name()), gym));
    }

    private void addTrainingSpecification(String training) {
        if (training == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(GymTraining.training())
                                                                                       .get(Training.name()), training));
    }

    private void addPriceSpecification(Double price) {
        if (price == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GymTraining.price()), price));
    }

    private void addMinParticipantsSpecification(Integer minParticipants) {
        if (minParticipants == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GymTraining.minParticipants()),
                                                                                   minParticipants));
    }

    private void addMaxParticipantsSpecification(Integer maxParticipants) {
        if (maxParticipants == null)
            return;

        specifications.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GymTraining.maxParticipants()),
                                                                                   maxParticipants));
    }

}

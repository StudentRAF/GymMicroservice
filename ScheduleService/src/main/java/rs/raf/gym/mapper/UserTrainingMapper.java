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

package rs.raf.gym.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.raf.gym.commons.dto.user_training.UserTrainingCreateDto;
import rs.raf.gym.commons.dto.user_training.UserTrainingDto;
import rs.raf.gym.commons.dto.user_training.UserTrainingUpdateDto;
import rs.raf.gym.model.UserTraining;

@Component
@AllArgsConstructor
public class UserTrainingMapper {

    private final TrainingMapper trainingMapper;

    /**
     * Maps UserTraining to UserTrainingDto object.
     * @param userTraining user training
     * @return UserTrainingDto object
     */
    public UserTrainingDto toUserTrainingDto(UserTraining userTraining) {
        return new UserTrainingDto(userTraining.getClientId(),
                                   trainingMapper.toTrainingDto(userTraining.getTraining()),
                                   userTraining.getCount());
    }

    /**
     * Maps UserTrainingCreateDto to existing UserTraining object.
     * @param userTraining user training
     * @param createDto create dto
     * @return UserTraining object
     */
    public UserTraining map(UserTraining userTraining, UserTrainingCreateDto createDto) {
        userTraining.setClientId(createDto.getClientId());
        userTraining.setCount(0);

        return userTraining;
    }

    /**
     * Maps UserTrainingUpdateDto to existing UserTraining object.
     * @param userTraining user training
     * @param updateDto update dto
     * @return UserTraining object
     */
    public UserTraining map(UserTraining userTraining, UserTrainingUpdateDto updateDto) {
        userTraining.setCount(updateDto.getCount());

        return userTraining;
    }

}

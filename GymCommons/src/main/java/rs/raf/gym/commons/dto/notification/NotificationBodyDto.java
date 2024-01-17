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

package rs.raf.gym.commons.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.raf.gym.commons.dto.user.UserDto;
import rs.raf.gym.commons.message_broker.MailFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationBodyDto {

    private String subject;

    private UserDto user;

    private String token;

    private Long userId;

    private LocalDate trainingDate;

    private String trainingName;

    private MailFormat mailFormat;

    public NotificationBodyDto(String subject, UserDto user, String token, Long userId, MailFormat mailFormat) {
        this.subject = subject;
        this.user = user;
        this.token = token;
        this.userId = userId;
        this.mailFormat = mailFormat;
    }

    public NotificationBodyDto(String subject, UserDto user, Long userId, LocalDate trainingDate, String trainingName, MailFormat mailFormat) {
        this.subject      = subject;
        this.user         = user;
        this.userId       = userId;
        this.trainingDate = trainingDate;
        this.trainingName = trainingName;
        this.mailFormat   = mailFormat;
    }

}

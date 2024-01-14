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

package rs.raf.gym.commons.message_broker;

import lombok.Getter;

@Getter
public enum MailFormat {
    //TODO It should not be in this packet
    REGISTER_MAIL("REGISTER_MAIL"),
    REQUEST_TRAINING ("REQUEST_TRAINING"),
    CHANGE_PASSWORD("CHANGE_PASSWORD"),
    CANCELED_TRAINING("CANCELED_TRAINING"),
    REMINDER("REMINDER");

    private final String name;

    MailFormat(String name) {
        this.name = name;
    }
}

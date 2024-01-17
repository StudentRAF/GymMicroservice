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

package rs.raf.gym.commons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum AppointmentStatusType {

    PENDING ("Pending" ),
    CANCELED("Canceled"),
    HELD    ("Held"    );

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }

    public static AppointmentStatusType findStatus(String name) {
        for (AppointmentStatusType status : AppointmentStatusType.values())
            if (status.name.equals(name))
                return status;

        return null;
    }

}

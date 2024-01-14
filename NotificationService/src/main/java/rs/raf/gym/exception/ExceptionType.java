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

package rs.raf.gym.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import rs.raf.gym.commons.exception.IException;
import rs.raf.gym.commons.exception.Severity;

@AllArgsConstructor
public enum ExceptionType implements IException {

    MAIL_FORMAT_UNKNOWN ("Couldn't find NotificationType. Type with name \"{0}\" doesn't exist", Severity.WARNING, HttpStatus.BAD_REQUEST);

    private final String     pattern;
    private final Severity severity;
    private final HttpStatus httpStatus;

    @Override
    public String pattern() {
        return pattern;
    }

    @Override
    public HttpStatusCode httpStatus() {
        return httpStatus;
    }

    @Override
    public Severity severity() {
        return severity;
    }
}

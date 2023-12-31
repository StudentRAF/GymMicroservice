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

package rs.raf.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import rs.raf.gym.commons.exception.IException;
import rs.raf.gym.commons.exception.Severity;

public enum ExceptionType implements IException {

    FIND_ID_USER_NOT_FOUND_USER ("Could not found User. User with id \"{0}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_ADMIN_NOT_FOUND_USER_ROLE("Could not create Admin. User Role with name \"{0}\" does not exist", Severity.ERROR, HttpStatus.BAD_REQUEST),
    CREATE_CLIENT_NOT_FOUND_USER_ROLE("Could not create Client. User Role with name \"{0}\" does not exist", Severity.ERROR, HttpStatus.BAD_REQUEST),
    CREATE_MANAGER_NOT_FOUND_USER_ROLE("Could not create Manager. User Role with name \"{0}\" does not exist", Severity.ERROR, HttpStatus.BAD_REQUEST),
    UPDATE_USER_NOT_FOUND_USERNAME("Could not update User data. User with username \"{0}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_NOT_FOUND_USERNAME("Could not update Client data. User with username \"{0}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_MANAGER_NOT_FOUND_USERNAME("Could not update Manager data. User with username \"{0}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    LOGIN_USER_NOT_FOUND_USERNAME_AND_PASSWORD("Could not login User. User with username \"{0}\" and password \"{1}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    LOGIN_USER_NOT_ACTIVATED("Could not login User. User with username \"{0}\" is not activated", Severity.WARNING, HttpStatus.BAD_REQUEST),
    LOGIN_USER_NOT_ACCESS("Could not login User. User with username \"{0}\" does not has access", Severity.WARNING, HttpStatus.BAD_REQUEST),
    FIND_ROLE_NOT_FOUND_USER_ROLE("Could not find User Role", Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_USER_ROLE_NOT_FOUND_USER_ROLE("Could not find User Role. User Role with name \"{0}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST);

    private final String     pattern;
    private final Severity   severity;
    private final HttpStatus httpStatus;

    ExceptionType(String pattern, Severity severity, HttpStatus httpStatus) {
        this.pattern    = pattern;
        this.severity   = severity;
        this.httpStatus = httpStatus;
    }

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

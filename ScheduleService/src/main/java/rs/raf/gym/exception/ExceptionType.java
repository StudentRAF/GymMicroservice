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

    FIND_GYM_ID_NOT_FOUND_GYM("Could not find Gym id. Gym with name \"{0}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    FIND_GYM_NOT_FOUND_GYM_ID("Could not find Gym. Gym with id \"{0}\" does not exist",      Severity.WARNING, HttpStatus.BAD_REQUEST),

    CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM                      ("Could not create Client Training Appointment. Gym with name \"{0}\" does not exist",                                                                        Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING                 ("Could not create Client Training Appointment. Training with name \"{0}\" does not exist",                                                                   Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING             ("Could not create Client Training Appointment. Gym Training with gym \"{0}\" and training \"{1}\" does not exist",                                           Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING_APPOINTMENT     ("Could not create Client Training Appointment. Training Appointment with gym \"{0}\" and training \"{1}\" and date \"{2}\" and time \"{3}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_CLIENT_APPOINTMENT_STATUS("Could not create Client Training Appointment. Client Appointment status with name \"{0}\" does not exist",                                                  Severity.ERROR,   HttpStatus.BAD_REQUEST),
    CREATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_USER                     ("Could not create Client Training Appointment. User with id \"{0}\" does not exist",                                                                         Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_GYM_TRAINING_NOT_FOUND_GYM                                     ("Could not create Gym Training. Gym with name \"{0}\" does not exist",                                                                                       Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_GYM_TRAINING_NOT_FOUND_TRAINING                                ("Could not create Gym Training. Training with name \"{0}\" does not exist",                                                                                  Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM                             ("Could not create Training Appointment. Gym with name \"{0}\" does not exist",                                                                               Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING                        ("Could not create Training Appointment. Training with name \"{0}\" does not exist",                                                                          Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING                    ("Could not create Training Appointment. Gym Training with gym \"{0}\" and training \"{1}\" does not exist",                                                  Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_TRAINING_APPOINTMENT_NOT_FOUND_APPOINTMENT_STATUS              ("Could not create Training Appointment. Appointment Status with name \"{0}\" does not exist",                                                                Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_TRAINING_NOT_FOUND_TRAINING_TYPE                               ("Could not create Training. Training Type with name \"{0}\" does not exist",                                                                                 Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_USER_TRAINING_NOT_FOUND_TRAINING                               ("Could not create User Training. Training with name \"{0}\" does not exist",                                                                                 Severity.WARNING, HttpStatus.BAD_REQUEST),
    CREATE_USER_TRAINING_NOT_FOUND_USER                                   ("Could not create User Training. User with id \"{0}\" does not exist",                                                                                       Severity.WARNING, HttpStatus.BAD_REQUEST),

    UPDATE_APPOINTMENT_STATUS_NOT_FOUND_APPOINTMENT_STATUS                  ("Could not update Appointment Status data. Appointment status with name \"{0}\" does not exist",                                                                                          Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_APPOINTMENT_STATUS_NOT_FOUND_CLIENT_APPOINTMENT_STATUS    ("Could not update Client Appointment Status data. Client Appointment status with name \"{0}\" does not exist",                                                                            Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM                        ("Could not update Client Training Appointment data. Gym with name \"{0}\" does not exist",                                                                                                Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING                   ("Could not update Client Training Appointment data. Training with name \"{0}\" does not exist",                                                                                           Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING               ("Could not update Client Training Appointment data. Gym Training with gym \"{0}\" and training \"{1}\" does not exist",                                                                   Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING_APPOINTMENT       ("Could not update Client Training Appointment data. Training Appointment with gym \"{0}\" and training \"{1}\" and date \"{2}\" and time \"{3}\" does not exist",                         Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_CLIENT_APPOINTMENT_STATUS  ("Could not update Client Training Appointment data. Client Appointment status with name \"{0}\" does not exist",                                                                          Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_USER                       ("Could not update Client Training Appointment data. User with id \"{0}\" does not exist",                                                                                                 Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_CLIENT_TRAINING_APPOINTMENT_NOT_FOUND_CLIENT_TRAINING_APPOINTMENT("Could not update Client Training Appointment data. Client Training Appointment with gym \"{0}\" and training \"{1}\" and date \"{2}\" and time \"{3}\" and user \"{4}\" does not exist", Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_GYM_NOT_FOUND_GYM                                                ("Could not update Gym data. Gym with name \"{0}\" does not exist",                                                                                                                        Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_GYM_MANAGER_NOT_FOUND_GYM                                        ("Could not update Gym manager. Gym with name \"{0}\" does not exist",                                                                                                                     Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_GYM_TRAINING_NOT_FOUND_GYM                                       ("Could not update Gym Training data. Gym with name \"{0}\" does not exist",                                                                                                               Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_GYM_TRAINING_NOT_FOUND_TRAINING                                  ("Could not update Gym Training data. Training with name \"{0}\" does not exist",                                                                                                          Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_GYM_TRAINING_NOT_FOUND_GYM_TRAINING                              ("Could not update Gym Training data. Gym Training with gym \"{0}\" and training \"{1}\" does not exist",                                                                                  Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM                               ("Could not update Training Appointment data. Gym with name \"{0}\" does not exist",                                                                                                       Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING                          ("Could not update Training Appointment data. Training with name \"{0}\" does not exist",                                                                                                  Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_GYM_TRAINING                      ("Could not update Training Appointment data. Gym Training with gym \"{0}\" and training \"{1}\" does not exist",                                                                          Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_APPOINTMENT_STATUS                ("Could not update Training Appointment data. Appointment Status with name \"{0}\" does not exist",                                                                                        Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_APPOINTMENT_NOT_FOUND_TRAINING_APPOINTMENT              ("Could not update Training Appointment data. Training Appointment with gym \"{0}\" and training \"{1}\" and date \"{2}\" and time \"{3}\" does not exist",                                Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_NOT_FOUND_TRAINING_TYPE                                 ("Could not update Training data. Training Type with name \"{0}\" does not exist",                                                                                                         Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_NOT_FOUND_TRAINING                                      ("Could not update Training data. Training with name \"{0}\" does not exist",                                                                                                              Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_TRAINING_TYPE_NOT_FOUND_TRAINING_TYPE                            ("Could not update Training Type data. Training Type with name \"{0}\" does not exist",                                                                                                    Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_USER_TRAINING_NOT_FOUND_TRAINING                                 ("Could not update User Training data. Training with name \"{0}\" does not exist",                                                                                                         Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_USER_TRAINING_NOT_FOUND_USER                                     ("Could not update User Training data. User with id \"{0}\" does not exist",                                                                                                               Severity.WARNING, HttpStatus.BAD_REQUEST),
    UPDATE_USER_TRAINING_NOT_FOUND_USER_TRAINING                            ("Could not update User Training data. User Training with training \"{0}\" and user \"{1}\" does not exist",                                                                               Severity.WARNING, HttpStatus.BAD_REQUEST);

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

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class Converter {

    private final Validator validator;
    private final ObjectMapper objectMapper;

    public <TypeSend> String serialize(TypeSend send) {
        try {
            return objectMapper.writeValueAsString(send);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public <TypeReturn> TypeReturn deserialize(Message message, Class<TypeReturn> classType) {
        try {
            String json = ((TextMessage) message).getText();
            TypeReturn object = objectMapper.readValue(json, classType);

            Set<ConstraintViolation<TypeReturn>> errors = validator.validate(object);

            if (errors.isEmpty())
                return object;

            printViolationsAndThrowException(errors);
            return null;
        } catch (JMSException | JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private <Type> void printViolationsAndThrowException(Set<ConstraintViolation<Type>> errors) {
        String concatenatedViolations = errors.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        throw new RuntimeException(concatenatedViolations);
    }
}

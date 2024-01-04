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

package rs.raf.gym;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import rs.raf.gym.commons.exception.GymException;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:config/spring/local.properties"),
        @PropertySource("classpath:config/spring/application.properties")
})
public class ServiceOrigin {

    static {
        TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MCwicm9sZSI6IlNlcnZpY2UifQ.JfgE3vdcKA0mnrm2o0qmvVx5UGfona19DfBsqROyDHg";
    }

    public static final String TOKEN;

    public static void main(String[] args) {
        GymException.setLogger(LoggerFactory.getLogger(ServiceOrigin.class));
        SpringApplication.run(ServiceOrigin.class, args);
    }

}

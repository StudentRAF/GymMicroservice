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
import org.springframework.scheduling.annotation.EnableAsync;
import rs.raf.gym.commons.exception.GymException;

@EnableAsync
@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:config/spring/local.properties"),
        @PropertySource("classpath:config/spring/application.properties")
})
public class UserMain {
    
    public static void main(String[] args) {
        GymException.setLogger(LoggerFactory.getLogger(UserMain.class));
        SpringApplication.run(UserMain.class, args);
    }

}
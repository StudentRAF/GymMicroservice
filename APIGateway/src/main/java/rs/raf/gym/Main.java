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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:config/spring/local.properties"),
        @PropertySource("classpath:config/spring/application.properties")
})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                      .route(predicate -> predicate.path("/api/schedule/**")
                                                   .filters(filter -> filter.rewritePath("/api/schedule/(?<route>.*)", "/schedule/${route}"))
                                                   .uri("lb://SCHEDULE-SERVICE"))
                      .route(predicate -> predicate.path("/api/user/**")
                                                   .filters(filter -> filter.rewritePath("/api/user/(?<route>.*)", "/user/${route}"))
                                                   .uri("lb://USER-SERVICE"))
                      .route(predicate -> predicate.path("/api/notification/**")
                                                   .filters(filter -> filter.rewritePath("/api/notification/(?<route>.*)", "/notification/${route}"))
                                                   .uri("lb://NOTIFICATION-SERVICE"))
                      .build();
    }

}

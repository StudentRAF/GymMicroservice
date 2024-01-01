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

package rs.raf.gym.commons.utils;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

public class NetworkUtils {

    private static final WebClient apiGateway = WebClient.create("http://localhost:8000/api");

    public static <Type> Type request(RequestMethod method, String uri, String token, Class<Type> typeClass) {
        return switch (method) {
            case GET  -> apiGateway.get()
                                   .uri(uri)
                                   .header("authorization", token)
                                   .accept(MediaType.APPLICATION_JSON)
                                   .retrieve()
                                   .bodyToMono(typeClass)
                                   .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
                                   .block();
            case PUT  -> apiGateway.put()
                                   .uri(uri)
                                   .header("authorization", token)
                                   .accept(MediaType.APPLICATION_JSON)
                                   .retrieve()
                                   .bodyToMono(typeClass)
                                   .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
                                   .block();
            case POST -> apiGateway.post()
                                   .uri(uri)
                                   .header("authorization", token)
                                   .accept(MediaType.APPLICATION_JSON)
                                   .retrieve()
                                   .bodyToMono(typeClass)
                                   .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
                                   .block();
            default -> null;
        };
    }

}

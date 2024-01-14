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

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class NetworkUtils {

    private final WebClient apiGateway = WebClient.create("http://localhost:8000/api");

    public <TypeReturn> TypeReturn request(HttpMethod method, String uri, String token, Class<TypeReturn> returnClass) {
        return apiGateway.method(method)
                         .uri(uri)
                         .header("authorization", token)
                         .accept(MediaType.APPLICATION_JSON)
                         .retrieve()
                         .bodyToMono(returnClass)
                         .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
                         .block();
    }

    public <TypeReturn> TypeReturn request(HttpMethod method, String uri, String token, Object content, Class<TypeReturn> returnClass) {
        if (content == null)
            return null;

        return apiGateway.method(method)
                         .uri(uri)
                         .header("authorization", token)
                         .contentType(MediaType.APPLICATION_JSON)
                         .bodyValue(content)
                         .accept(MediaType.APPLICATION_JSON)
                         .retrieve()
                         .bodyToMono(returnClass)
                         .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
                         .block();
    }

    @Async
    public <TypeReturn> CompletableFuture<TypeReturn> asyncRequest(HttpMethod method, String uri, String token, Class<TypeReturn> returnClass) {
        return CompletableFuture.completedFuture(apiGateway.method(method)
                                .uri(uri)
                                .header("authorization", token)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(returnClass)
                                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
                                .block());
    }

    @Async
    public <TypeReturn> CompletableFuture<TypeReturn> asyncRequest(HttpMethod method, String uri, String token, Object content, Class<TypeReturn> returnClass) {
        if (content == null)
            return null;

        return CompletableFuture.completedFuture(apiGateway.method(method)
                                .uri(uri)
                                .header("authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(content)
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(returnClass)
                                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
                                .block());
    }

}

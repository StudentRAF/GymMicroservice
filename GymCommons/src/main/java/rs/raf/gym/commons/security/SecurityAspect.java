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

package rs.raf.gym.commons.security;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import rs.raf.gym.commons.model.Role;
import rs.raf.gym.commons.security.service.ITokenService;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@AllArgsConstructor
public class SecurityAspect {

    private final ITokenService tokenService;

    /**
     * Checks if authorization is correct |
     * Method to be called before each method that has CheckSecurity annotation
     * @param joinPoint Can allow original method to continue | Has information about original method
     * @return 500 https status or original method can continue
     */
    @Around("@annotation(rs.raf.gym.commons.security.CheckSecurity)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature =(MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String token = null;

        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if (joinPoint.getArgs()[i] == null)
                continue;

            String paramName = methodSignature.getParameterNames()[i];
            if (! paramName.equals("token"))
                continue;

            token = joinPoint.getArgs()[i].toString();
        }

        Role role = getRole(token);

        if (role == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);

        if (! Arrays.asList(checkSecurity.role()).contains(role))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return joinPoint.proceed();
    }

    public Role getRole(String token) {
        System.out.println("Token: " + token);
        if (token == null)
            return null;

        Claims payload = tokenService.decipherToken(token);

        if (payload == null)
            return null;

        System.out.println("Id: " + payload.get("id", Long.class));
        System.out.println("Role: " + payload.get("userRole", String.class));
        return Role.findRole(payload.get("userRole", String.class));
    }

    public Long getId(String token) {
        if (token == null)
            return null;

        Claims payload = tokenService.decipherToken(token);

        if (payload == null)
            return null;

        return payload.get("id", Long.class);
    }
}

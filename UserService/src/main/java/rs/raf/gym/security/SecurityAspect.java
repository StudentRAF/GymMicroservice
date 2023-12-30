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

package rs.raf.gym.security;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rs.raf.gym.commons.dto.user.UserTokenDto;
import rs.raf.gym.commons.dto.user_role.UserRoleDto;
import rs.raf.gym.model.Roles;
import rs.raf.gym.model.User;
import rs.raf.gym.security.service.ITokenService;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class SecurityAspect {

    private ITokenService tokenService;

    public SecurityAspect(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Checks if authorization is correct |
     * Method to be called before each method that has CheckSecurity annotation
     * @param joinPoint Can allow original method to continue | Has information about original method
     * @return 500 https status or original method can continue
     */
    @Around("@annotation(rs.raf.gym.security.CheckSecurity)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature =(MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String token = null;

        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
            String paramName = methodSignature.getParameterNames()[i];
            String paramContent = joinPoint.getArgs()[i].toString();
            if (! paramName.equals("authorization"))
                continue;

            if (! paramContent.startsWith("Bearer"))
                continue;

            token = paramContent.split(" ")[1];
        }

        if (token == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Claims payload = tokenService.decipherToken(token);

        if (payload == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setName(payload.get(User.userRole(), String.class));

        if (! Arrays.asList(checkSecurity.roles()).contains(Roles.findRole(userRoleDto.getName())))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return joinPoint.proceed();
    }
}

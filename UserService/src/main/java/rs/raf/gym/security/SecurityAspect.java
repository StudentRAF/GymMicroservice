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
import org.springframework.context.annotation.Configuration;
import rs.raf.gym.commons.model.Role;
import rs.raf.gym.model.User;
import rs.raf.gym.security.service.ITokenService;

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
//        MethodSignature methodSignature =(MethodSignature)joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        String token = null;
//
//        for (int i = 0; i < methodSignature.getParameterNames().length; i++) {
//            String paramName = methodSignature.getParameterNames()[i];
//            String paramContent = joinPoint.getArgs()[i].toString();
//            if (! paramName.equals("authorization"))
//                continue;
//
//            if (! paramContent.startsWith("Bearer"))
//                continue;
//
//            token = paramContent;
//        }
//
//        Roles role = getRole(token);
//
//        if (role == null)
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//
//        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);
//
//        if (! Arrays.asList(checkSecurity.roles()).contains(role))
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return joinPoint.proceed();
    }

    public Role getRole(String token) {
        if (token == null)
            return null;

//        token = token.split(" ")[1];
        Claims payload = tokenService.decipherToken(token);

        if (payload == null)
            return null;

        return Role.findRole(payload.get(User.userRole(), String.class));
    }

    public Long getId(String token) {
        if (token == null)
            return null;

//        token = token.split(" ")[1];
        Claims payload = tokenService.decipherToken(token);

        if (payload == null)
            return null;

        return payload.get(User.id(), Long.class);
    }
}

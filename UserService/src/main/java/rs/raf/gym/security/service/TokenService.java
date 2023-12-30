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

package rs.raf.gym.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Map;

import static io.jsonwebtoken.Jwts.SIG.HS512;

@Service
public class TokenService implements ITokenService{

   // @Value("${user.secret.key}")
    private String jwtSecret;
    private SecretKey secretKey;

    public TokenService() {
        this.jwtSecret = HS512.key().toString();

        byte[] secretKeyBytes = jwtSecret.getBytes();
        secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    @Override
    public String encrypt(Map<String, Object> payload) {
        return Jwts.builder().claims(payload).signWith(secretKey).compact();
    }

    @Override
    public Claims decipherToken(String token) {
        Claims payload;
        try {
            payload = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        }
        catch (Exception e) {
            return null;
        }

        return payload;
    }
}

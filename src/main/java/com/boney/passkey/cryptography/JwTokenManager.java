package com.boney.passkey.cryptography;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class JwTokenManager {
    private static final SecretKey SECRET_KEY = generateSecretKey();

    public String generatePasskey(String userId, String publicKey, byte[] cipherKey) throws Exception {
        String encryptedPayload = Base64.getEncoder().encodeToString(
                new EncryptionManager().encrypt(cipherKey, userId + ":" + publicKey)
        );

        return Jwts.builder()
                .setSubject(encryptedPayload)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SECRET_KEY)
                .compact();
    }

    private static SecretKey generateSecretKey() {
        byte[] keyBytes = new byte[32];
        new SecureRandom().nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean verifyPasskey(String userId, String passkey, byte[] cipherKey) throws Exception {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(passkey);

            String encryptedPayload = claims.getBody().getSubject();
            String decryptedPayload = new EncryptionManager().decrypt(cipherKey, Base64.getDecoder().decode(encryptedPayload));

            String[] parts = decryptedPayload.split(":");
            String decryptedUserId = parts[0];
            String decryptedPublicKey = parts[1];

            return userId.equals(decryptedUserId) && !decryptedPublicKey.isEmpty() &&
                    new Date().before(claims.getBody().getExpiration());
        } catch (Exception e) {
            return false;
        }
    }
}

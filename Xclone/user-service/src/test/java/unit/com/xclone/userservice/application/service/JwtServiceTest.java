package unit.com.xclone.userservice.application.service;


import com.xclone.userservice.application.service.impl.JwtServiceImpl;
import com.xclone.userservice.repository.db.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.crypto.SecretKey;
import java.util.Date;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private static final String SECRET = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9";

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Test
    public void testExtractUserData() {
        String token = jwtService.generateToken("tranhung@gmail.com");
        // Execute
        Map<String, String> userData = jwtService.extractUserData(token);

        // Verify
        assertEquals("tranhung@gmail.com", userData.get("email"));
    }

    @Test
    public void testExtractUserDataByField() {
        // Prepare
        String token = jwtService.generateToken("tranhung@gmail.com");

        // Execute
        String email = jwtService.extractUserDataByField(token, "email");

        // Verify
        assertEquals("tranhung@gmail.com", email);
    }

    @Test
    public void testIsTokenValid() {
        // Prepare
        String token = jwtService.generateToken("tranhung@gmail.com");

        // Execute
        boolean isValid = jwtService.isTokenValid(token);

        // Verify
        assertTrue(isValid);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken("test@example.com");

        // Verify
        assertTrue(token.length() > 0);
    }
}
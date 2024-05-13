package unit.com.xclone.userservice.application.service;

import com.xclone.userservice.application.service.impl.AuthenticationServiceImpl;
import com.xclone.userservice.application.service.impl.JwtServiceImpl;
import com.xclone.userservice.error.BadRequestException;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import com.xclone.userservice.requestDto.AuthenticationRequest;
import com.xclone.userservice.requestDto.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;
import java.util.UUID;


import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void login_givenRightEmailPassword_returnExpected() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest
                .builder()
                .email("tranhung@gmail.com")
                .password("123456")
                .build();

        var token = "eyJhbGciOiJIUzM4NCJ9.eyJleHAiOjE3MTQ0NzM1OTgsImZ1bGxOYW1lIjoidHJhbiBodW5nIiwiZW1haWwiOiJ0cmFuaHVuZzFAZ21haWwuY29tIn0.sGZJnivKi13y9MFMR4EQFq1WvV2G5OLbs0TBLFRgUVgQAr2umsvWY5wEkYPOrsaz";

        User user = User.builder().id(UUID.randomUUID()).fullName("tran hung").email("tranhung@gmail.com").build();

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()))).thenReturn(Mockito.mock(Authentication.class));

        when(jwtService.generateToken(user.getEmail())).thenReturn(token);

        assertEquals(token, authenticationService.login(authenticationRequest).getToken());
    }

    @Test
    void login_givenWrongEmailOrPassword_returnExpected() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest
                .builder()
                .email("tranhung@gmail.com")
                .password("123456")
                .build();

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()))).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class,() -> authenticationService.login(authenticationRequest));
    }

    @Test
    void registration_givenNotExistEmail_returnExpected(){
        RegistrationRequest request = RegistrationRequest.builder()
                .fullName("tran hung")
                .email("tranhung@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();


        when(userRepository.existsUserByEmail("tranhung@gmail.com")).thenReturn(false);

        assertDoesNotThrow(() -> authenticationService.registration(request));

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
    }

    @Test
    void registration_givenExistEmail_throwException(){
        RegistrationRequest request = RegistrationRequest.builder()
                .fullName("tran hung")
                .email("tranhung@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();


        when(userRepository.existsUserByEmail("tranhung@gmail.com")).thenReturn(true);

        assertThrows(BadRequestException.class ,() -> authenticationService.registration(request));
    }
}

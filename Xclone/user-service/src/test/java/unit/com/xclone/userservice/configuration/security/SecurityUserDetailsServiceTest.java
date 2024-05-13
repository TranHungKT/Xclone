package unit.com.xclone.userservice.configuration.security;

import com.xclone.userservice.configuration.security.SecurityUserDetailsService;
import com.xclone.userservice.error.BadRequestException;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class SecurityUserDetailsServiceTest {
    @InjectMocks
    private SecurityUserDetailsService securityUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_givenExistUserByEmail_returnUserData() {
        when(userRepository.findUserByEmail("tranhung@gmail.com"))
                .thenReturn(Optional.ofNullable(
                        User.builder()
                                .email("tranhung@gmail.com")
                                .role("USER_ROLE")
                                .build()));

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("tranhung@gmail.com");

        assertNotNull(userDetails);
    }

    @Test
    void loadUserByUsername_givenNotExistUserByEmail_throwException() {
        when(userRepository.findUserByEmail("tranhung@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> securityUserDetailsService.loadUserByUsername("tranhung@gmail.com"));
    }
}

package unit.com.xclone.userservice.application.service;

import com.xclone.userservice.application.service.impl.UserServiceImpl;
import com.xclone.userservice.error.BadRequestException;
import com.xclone.userservice.repository.db.dao.UserImageRepository;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Test
    void getUserById_givenValidId_returnExpected() {
        UUID validUserId = UUID.fromString("96751bae-00d0-4b73-b59f-4ffa8112e04c");
        when(userRepository.findByUserId(validUserId)).thenReturn(Optional.ofNullable(User.builder().userId(validUserId).username("tran hung").build()));
        when(userImageRepository.findByUserId(validUserId)).thenReturn(Optional.empty());
        var actual = userService.getUserById(validUserId);

        assertEquals(actual.getId(), validUserId);
        assertEquals(actual.getUsername(), "tran hung");
    }

    @Test
    void getUserById_givenInValidId_returnException() {
        UUID validUserId = UUID.fromString("96751bae-00d0-4b73-b59f-4ffa8112e04c");
        when(userRepository.findByUserId(validUserId)).thenReturn(Optional.empty());

        assertThrows(
                BadRequestException.class,
                () -> userService.getUserById(validUserId)
        );
    }
}

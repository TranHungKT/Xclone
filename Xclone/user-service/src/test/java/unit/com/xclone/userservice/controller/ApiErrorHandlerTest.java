package unit.com.xclone.userservice.controller;

import com.xclone.userservice.controller.ApiErrorHandler;
import com.xclone.userservice.error.ApiErrorDetails;
import com.xclone.userservice.error.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class ApiErrorHandlerTest {
    @InjectMocks
    private ApiErrorHandler apiErrorHandler;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Test
    void handleBadRequestException_givenException_returnBadRequestResponse(){
        var errorDetails = ApiErrorDetails.builder().build();
        var exception = new BadRequestException(Collections.singletonList(errorDetails));
        var actual = apiErrorHandler.handleBadRequestException(exception, httpServletRequest);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertEquals("XCLONE_API_ERROR_INVALID_REQUEST", Objects.requireNonNull(actual.getBody()).getErrorId());
        assertEquals("Invalid request - user service", actual.getBody().getMessage());


    }
}

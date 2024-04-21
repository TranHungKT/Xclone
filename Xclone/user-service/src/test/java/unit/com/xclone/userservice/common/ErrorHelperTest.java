package unit.com.xclone.userservice.common;

import com.xclone.userservice.common.ErrorHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ErrorHelperTest {
    @Test
    void buildBadRequestException_givenMessage_returnError(){
        var actual = ErrorHelper.buildBadRequestException("id", "invalid id", "123456");

        assertEquals("XCLONE_API_ERROR_INVALID_REQUEST", actual.getErrorId());
        assertEquals("Invalid request - user service", actual.getMessage());
        assertEquals("id", actual.getErrorDetails().get(0).getField());
        assertEquals("invalid id", actual.getErrorDetails().get(0).getIssue());
        assertEquals("123456", actual.getErrorDetails().get(0).getValue());
    }
}

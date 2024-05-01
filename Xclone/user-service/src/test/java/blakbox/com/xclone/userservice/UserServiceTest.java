package blakbox.com.xclone.userservice;

import blakbox.com.xclone.userservice.common.CommonTestData;
import blakbox.com.xclone.userservice.common.TestBase;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest extends TestBase {
    @BeforeAll
    void setUp() throws SQLException {
        runScriptFromResource("blackbox/user.sql");
    }

//    Do not need to clear data for now
//    @AfterAll
//    void tearDown() throws SQLException{
//        runScriptFromResource();
//    }


    @ParameterizedTest
    @CsvSource(value = {
            "96751bae-00d0-4b73-b59f-4ffa8112e04c|user_details",
    }, delimiter = '|')
    void givenUserId_whenCallGetUserDetails_returnExcepted(String userId, String responseFile) throws IOException, JSONException {
        var actualResponse = getUserDetails(userId).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_USER_DETAILS_RESPONSE  + responseFile);
        assertEquals(expectedResponse,actualResponse, JSONCompareMode.STRICT);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "96751bae-00d0-4b73-b59f-4ffa8112e321|invalid_user"
    }, delimiter = '|')
    void givenInvalidUserId_whenCallGetUserDetails_returnException(String userId, String responseFile) throws IOException, JSONException {
        var actualResponse = getUserDetails(userId).then().log()
                .body()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_USER_DETAILS_RESPONSE  + responseFile);
        assertEquals(expectedResponse,actualResponse, JSONCompareMode.STRICT);
    }
}

package blakbox.com.xclone.userservice;

import blakbox.com.xclone.userservice.common.CommonTestData;
import blakbox.com.xclone.userservice.common.TestBase;
import com.xclone.userservice.application.service.JwtService;
import com.xclone.userservice.repository.db.dao.UserRepository;
import com.xclone.userservice.repository.db.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest extends TestBase {
    private String token;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @BeforeAll
    void setUp() throws SQLException, IOException {
        runScriptFromResource("blackbox/user.sql");
        token = loginUserAndExtractToken();
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
        var actualResponse = getUserDetails(userId, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_USER_DETAILS_RESPONSE + responseFile);

        assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "96751bae-00d0-4b73-b59f-4ffa8112e321|invalid_user"
    }, delimiter = '|')
    void givenInvalidUserId_whenCallGetUserDetails_returnException(String userId, String responseFile) throws IOException, JSONException {
        var actualResponse = getUserDetails(userId, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_USER_DETAILS_RESPONSE + responseFile);
        assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "566abbd2-1d35-4d52-bd44-2ea3820fed56|followers"
    }, delimiter = '|')
    void givenUserId_getFollowers_returnExpected(String userId, String responseFile) throws IOException, JSONException {
        var actualResponse = getFollowers(userId, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_USER_DETAILS_RESPONSE + responseFile);
        assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "96751bae-00d0-4b73-b59f-4ffa8112e04c|followings"
    }, delimiter = '|')
    void givenUserId_getFollowings_returnExpected(String userId, String responseFile) throws IOException, JSONException {
        var actualResponse = getFollowings(userId, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_USER_DETAILS_RESPONSE + responseFile);
        assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "54a007cf-4ade-4a63-8380-1e4f9a14f5e0|follow_user|following_user_data",
            "54a007cf-4ade-4a63-8380-1e4f9a14f5e0|unfollow_user|unfollowing_user_data",
    }, delimiter = '|')
    @Transactional
    void givenUserId_updateUserStatus_returnExpected(UUID userId, String requestBodyFile, String dataFile) throws IOException {
        updateFollowingUserStatus(requestBodyFile, userId, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .body()
                .asString();
        var expectedFollowingUser = getListFromJsonFile(CommonTestData.POST_RESPONSE_USER_PATH + dataFile, User.class).stream().map(User::getUserId).toList();
        var userEmail = jwtService.extractUserDataByField(token, "email");
        var user = userRepository.findUserByEmail(userEmail);
        assertTrue(user.isPresent());
        var actualFollowing = user.get().getFollowing().stream().map(User::getUserId).toList();
        assertThat(expectedFollowingUser).containsExactlyInAnyOrderElementsOf(actualFollowing);
    }
}

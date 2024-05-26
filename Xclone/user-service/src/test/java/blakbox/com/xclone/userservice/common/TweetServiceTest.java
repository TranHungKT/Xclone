package blakbox.com.xclone.userservice.common;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TweetServiceTest extends TestBase {
    private String token;
    @BeforeAll
    void setUp() throws SQLException, IOException {
        runScriptFromResource("blackbox/tweet.sql");
        token = loginUserAndExtractToken();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "create_tweet_req",
    }, delimiter = '|')
    void givenUserId_whenCallGetUserDetails_returnExcepted(String reqBodyFile) throws IOException {
        createTweet(reqBodyFile, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .body()
                .asString();
    }
}

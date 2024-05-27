package blakbox.com.xclone.userservice.common;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TweetServiceTest extends TestBase {
    private String token;
    @BeforeEach
    void setUp() throws SQLException, IOException {
        runScriptFromResource("blackbox/tweet.sql");
        token = loginUserAndExtractToken();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "get_tweets_res",
    }, delimiter = '|')
    void givenGetTweetRequest_returnExpected(String resFileName) throws IOException, JSONException {
        String actualResponse =  getTweets( token).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_TWEETS_RESPONSE + resFileName);
        assertEquals(expectedResponse, actualResponse, true);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "create_tweet_req",
    }, delimiter = '|')
    void givenCreateTweetBody_returnExcepted(String reqBodyFile) throws IOException {
        createTweet(reqBodyFile, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .body()
                .asString();
    }


}

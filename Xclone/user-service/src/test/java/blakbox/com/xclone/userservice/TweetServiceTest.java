package blakbox.com.xclone.userservice;

import blakbox.com.xclone.userservice.common.CommonTestData;
import blakbox.com.xclone.userservice.common.TestBase;
import com.xclone.userservice.common.ErrorHelper;
import com.xclone.userservice.repository.db.dao.TweetImageRepository;
import com.xclone.userservice.repository.db.dao.TweetRepository;
import com.xclone.userservice.repository.db.entity.Tweet;
import com.xclone.userservice.repository.db.entity.User;
import jakarta.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TweetServiceTest extends TestBase {
    private String token;

    @Autowired
    private TweetRepository tweetRepository;

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
        String actualResponse = getTweets(token).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_TWEETS_RESPONSE + resFileName);
        assertEquals(expectedResponse, actualResponse, false);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "7645ebd0-6d94-4d72-94f3-2ef86dc2c48d|get_tweet_by_id_res",
    }, delimiter = '|')
    void givenGetTweetByIdRequest_returnExpected(String tweetId, String resFileName) throws IOException, JSONException {
        String actualResponse = getTweetById(tweetId, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

        String expectedResponse = readJsonContentFromResource(CommonTestData.GET_TWEETS_RESPONSE + resFileName);
        assertEquals(expectedResponse, actualResponse, false);
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

    @ParameterizedTest
    @CsvSource(value = {
            "7645ebd0-6d94-4d72-94f3-2ef86dc2c48d|like_tweet_req|like_tweet_data",
            "ef1f4fef-8d73-4bbb-99e0-adf65117fa5b|dislike_tweet_req|dislike_tweet_data",
    }, delimiter = '|')
    @Transactional
    void givenTweetId_reactTweet_returnExpected(UUID tweetId, String reqBodyFile, String dataFile) throws IOException {
        reactTweet(reqBodyFile, token, tweetId).then().log()
                .body()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .body()
                .asString();
        var expectedTweetReacted = getListFromJsonFile(CommonTestData.GET_TWEETS_RESPONSE  + dataFile, User.class).stream().map(User::getUserId).toList();
        var actualTweetReacted = tweetRepository.findByTweetId(tweetId);
        assertTrue(actualTweetReacted.isPresent());
        var tweetLikes = actualTweetReacted.get().getLikes().stream().map(User::getUserId).toList();
        assertThat(tweetLikes).containsExactlyInAnyOrderElementsOf(expectedTweetReacted);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "7645ebd0-6d94-4d72-94f3-2ef86dc2c48d|retweet_data",
    }, delimiter = '|')
    @Transactional
    void givenTweetId_retweet_returnExpected(UUID tweetId, String dataFile) throws IOException {
        retweet(tweetId, token).then().log()
                .body()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .body()
                .asString();
        var expectedTweetReacted = getListFromJsonFile(CommonTestData.GET_TWEETS_RESPONSE  + dataFile, User.class).stream().map(User::getUserId).toList();
        var actualTweetReacted = tweetRepository.findByTweetId(tweetId);
        assertTrue(actualTweetReacted.isPresent());
        var tweetLikes = actualTweetReacted.get().getRetweets().stream().map(User::getUserId).toList();
        assertThat(tweetLikes).containsExactlyInAnyOrderElementsOf(expectedTweetReacted);
    }
}

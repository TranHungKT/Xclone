package blakbox.com.xclone.userservice.common;

public class CommonTestData {
    private CommonTestData(){

    }

    public static final String BASE_PATH = "user-service";
    public static final String BASE_URI ="http://localhost";
    public static final Integer PORT = 8080;
    public static final String GET_USER_DETAILS = "/api/v1/users/%s";
    public static final String TWEET = "/api/v1/tweets";
    public static final String REACT_TWEET = "/api/v1/tweets/react/%s";
    public static final String UPDATE_FOLLOWING_STATUS = "/api/v1/users/follow-action/%s";
    public static final String TWEET_BY_ID = "/api/v1/tweets/%s";
    public static final String RETWEET_BY_ID = "/api/v1/tweets/retweet/%s";
    public static final String LOGIN = "/api/v1/auth/login";
    public static final String REGISTRATION = "/api/v1/auth/registration";
    public static final String REQUEST_LOGIN_PATH = "authentication/request/";
    public static final String REQUEST_TWEET_PATH = "tweet/request/";
    public static final String POST_REQUEST_USER_PATH = "user/post/request/";
    public static final String POST_RESPONSE_USER_PATH = "user/post/response/";
    public static final String GET_USER_DETAILS_RESPONSE = "user/get/response/";
    public static final String GET_TWEETS_RESPONSE = "tweet/response/";
}

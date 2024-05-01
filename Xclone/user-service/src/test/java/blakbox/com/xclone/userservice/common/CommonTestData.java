package blakbox.com.xclone.userservice.common;

public class CommonTestData {
    private CommonTestData(){

    }

    public static final String BASE_PATH = "user-service";
    public static final String BASE_URI ="http://localhost";
    public static final Integer PORT = 8080;
    public static final String GET_USER_DETAILS = "/api/v1/users/%s";
    public static final String LOGIN = "/api/v1/auth/login";
    public static final String REGISTRATION = "/api/v1/auth/registration";
    public static final String REQUEST_LOGIN_PATH = "authentication/request/";
    public static final String GET_USER_DETAILS_RESPONSE = "user/get/response/";
}

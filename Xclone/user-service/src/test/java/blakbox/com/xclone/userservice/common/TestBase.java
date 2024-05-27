package blakbox.com.xclone.userservice.common;

import com.xclone.userservice.Application;
import com.xclone.userservice.responseDto.LoginResponseDto;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest(classes = {Application.class})
public class TestBase {
    @Autowired
    private DataSource dataSource;

    protected String readJsonContentFromResource(String filePath) throws IOException {
        String resourcePath = String.format("blackbox/%s.json", filePath);
        Resource resource = new ClassPathResource(resourcePath);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }

    protected RequestSpecBuilder getSpecificationBuilder(Map<String ,String> headers){
        return new RequestSpecBuilder()
                .setBaseUri(CommonTestData.BASE_URI)
                .setBasePath(CommonTestData.BASE_PATH)
                .setPort(CommonTestData.PORT)
                .setAccept("*/*")
                .setContentType(ContentType.JSON)
                .addHeaders(headers)
                ;
    }

    protected Response getUserDetails(String userId, String token) throws IOException {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + token);
        RequestSpecification specification = getSpecificationBuilder(headers).build();

        return RestAssured.given().spec(specification).get(String.format(CommonTestData.GET_USER_DETAILS, userId));
    }

    protected Response createTweet(String reqBodyFile, String token) throws IOException {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + token);
        RequestSpecification specification = getSpecificationBuilder(headers).setBody(readJsonContentFromResource(CommonTestData.REQUEST_TWEET_PATH + reqBodyFile)).build();

        return RestAssured.given().spec(specification).post(CommonTestData.TWEET);
    }

    protected Response getTweets( String token) throws IOException {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + token);
        RequestSpecification specification = getSpecificationBuilder(headers).build();

        return RestAssured.given().spec(specification).get(CommonTestData.TWEET);
    }

    protected Response getTweetById(String id, String token) throws IOException {
        var headers = new HashMap<String, String>();
        headers.put("Authorization", "Bearer " + token);
        RequestSpecification specification = getSpecificationBuilder(headers).build();

        return RestAssured.given().spec(specification).get(String.format(CommonTestData.TWEET_BY_ID, id));
    }


    protected Response loginUser(String reqBodyFileName) throws IOException {
        RequestSpecification specification = getSpecificationBuilder(new HashMap<>()).setBody(readJsonContentFromResource(CommonTestData.REQUEST_LOGIN_PATH + reqBodyFileName)).build();

        return RestAssured.given().spec(specification).post(CommonTestData.LOGIN);
    }

    protected Response registrationUser(String reqBodyFileName) throws IOException {
        RequestSpecification specification = getSpecificationBuilder(new HashMap<>()).setBody(readJsonContentFromResource(CommonTestData.REQUEST_LOGIN_PATH + reqBodyFileName)).build();

        return RestAssured.given().spec(specification).post(CommonTestData.REGISTRATION);
    }

    protected String loginUserAndExtractToken() throws IOException {
        return loginUser("login_user_req").then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .as(LoginResponseDto.class)
                .getToken();
    }

    public String replaceTimeStamp(String text){
        return text.replaceAll("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+", "");
    }

    protected void runScriptFromResource(String fileName) throws SQLException {
        String resourcePath = String.format(fileName);
        try (Connection conn = dataSource.getConnection()){
            ScriptUtils.executeSqlScript(conn, new ClassPathResource(resourcePath));
        }
    }
}

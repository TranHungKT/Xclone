package blakbox.com.xclone.userservice;

import blakbox.com.xclone.userservice.common.TestBase;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationServiceTest extends TestBase {
    @BeforeAll
    void setUp() throws SQLException {
        runScriptFromResource("blackbox/user.sql");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "login_user_req"
    }, delimiter = '|')
    void givenUserExist_loginUser_returnSuccess(String reqBodyFileName) throws IOException {
        loginUser(reqBodyFileName).then().log()
                .body()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asString();

    }

    @ParameterizedTest
    @CsvSource(value = {
            "login_wrong_password_req"
    }, delimiter = '|')
    void givenInvalidData_loginUser_return403(String reqBodyFileName) throws IOException {
        String invalidFile = "invalid/" + reqBodyFileName;
        loginUser(invalidFile).then().log()
                .body()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .body()
                .asString();

    }

    @ParameterizedTest
    @CsvSource(value = {
            "login_wrong_email_req",
    }, delimiter = '|')
    void givenInvalidData_loginUser_return400(String reqBodyFileName) throws IOException {
        String invalidFile = "invalid/" + reqBodyFileName;
        loginUser(invalidFile).then().log()
                .body()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .body()
                .asString();

    }

    @ParameterizedTest
    @CsvSource(value = {
            "registration_req",
    }, delimiter = '|')
    void givenValidData_registrationUser_returnSuccess(String reqBodyFileName) throws IOException {
        registrationUser(reqBodyFileName).then().log()
                .body()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .extract()
                .body()
                .asString();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "registration_exist_email_req",
    }, delimiter = '|')
    void givenInvalidData_registrationUser_return400(String reqBodyFileName) throws IOException {
        String invalidFile = "invalid/" + reqBodyFileName;
        registrationUser(invalidFile).then().log()
                .body()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .body()
                .asString();
    }

}

package blakbox.com.xclone.userservice.common;

import com.xclone.userservice.UserServiceApplication;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest(classes = {UserServiceApplication.class})
@AutoConfigureWireMock(port = 8082)
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

    protected Response getUserDetails(String userId) throws IOException {
        RequestSpecification specification = getSpecificationBuilder(new HashMap<>()).build();

        return RestAssured.given().spec(specification).get(String.format(CommonTestData.GET_USER_DETAILS, userId));
    }
}

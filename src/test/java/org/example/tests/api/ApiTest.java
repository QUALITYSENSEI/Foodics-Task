package org.example.tests.api;

import org.example.base.api.APIBaseTest;
import org.example.utils.JsonReaderUtil;
import org.example.utils.PropertiesReaderUtil;
import org.example.utils.UserPayloadBuilder;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest extends APIBaseTest {

    private static String userId;
    private static final String userName = JsonReaderUtil.getValue("fullName");
    private static final String userJob = JsonReaderUtil.getValue("job");
    private static final String updatedJob = JsonReaderUtil.getValue("updatedJob");

    @Test(priority = 1)
    public void testCreateUser() {
        userId =
                given()
                        .header("Content-Type", "application/json")
                        .header("x-api-key", PropertiesReaderUtil.getProperty("api.key"))
                        .body(UserPayloadBuilder.userPayload(userName, userJob))
                        .when()
                        .post("/api/users")
                        .then().log().all()
                        .statusCode(201)
                        .body("name", equalTo(userName))
                        .body("job", equalTo(userJob))
                        .body("id", notNullValue())
                        .extract()
                        .path("id");
        System.out.println("Created userId = " + userId);
    }

    @Test(priority = 2, dependsOnMethods = "testCreateUser")
    public void testRetrieveUser() {
        given()
                .header("x-api-key", PropertiesReaderUtil.getProperty("api.key"))
                .when()
                .get("/api/users/" + userId)
                .then()
                .statusCode(200)
                .body("name", equalTo(userName))
                .body("job", equalTo(userJob));
    }

    // c. Update a User
    @Test(priority = 3, dependsOnMethods = "testCreateUser")
    public void testUpdateUser() {
        given()
                .header("Content-Type", "application/json")
                .header("x-api-key", PropertiesReaderUtil.getProperty("api.key"))
                .body(UserPayloadBuilder.userPayload(userName, updatedJob))
                .when()
                .put("/api/users/" + userId)
                .then().log().all()
                .statusCode(200)
                .body("name", equalTo(userName))
                .body("job", equalTo(updatedJob))
                .body("updatedAt", notNullValue());
    }
}

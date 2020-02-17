package de.vh.usermanagementdb;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import de.vh.usermanagementdb.model.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class UsermanagementdbApplicationLiveTest {

    private static final String API_ROOT
            = "http://localhost:8080/api";

    private User createTestUser() {
        User user = new User();
        user.setFirstName("Numerius");
        user.setLastName("Negidius");
        user.seteMail("numneg@rom.it");
        return user;
    }


    private String createUserAsUri(User user) {



        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }


    @Test
    public void whenCreateNewUser_thenCreated() {
        User user = createTestUser();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .post(API_ROOT+"/user/add");

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }



    @Test
    public void whenInvalidUser_thenError() {
        User user = createTestUser();
        user.setFirstName(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(user)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenGetAllUsers_responseOK() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }


}

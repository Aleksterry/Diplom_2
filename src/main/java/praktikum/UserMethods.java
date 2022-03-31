package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserMethods extends RestAssured {

    private final String USER_PATH = "api/auth/";

    @Step("Create user")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "register")
                .then()
                .log().all();
    }

    @Step("Login user")
    public ValidatableResponse loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(USER_PATH + "login")
                .then()
                .log().all();
    }

    @Step("Edit user")
    public ValidatableResponse editUser(User user, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(user)
                .when()
                .patch(USER_PATH + "user")
                .then()
                .log().all();
    }

    public ValidatableResponse getUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .patch(USER_PATH + "user")
                .then()
                .log().all();
    }

    public ValidatableResponse refreshToken(UserRefresh userRefresh) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(userRefresh)
                .patch(USER_PATH + "token")
                .then()
                .log().all();
    }

    @Step("Delete user")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .delete(USER_PATH + "user")
                .then()
                .log().all();
    }


}

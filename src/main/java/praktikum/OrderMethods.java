package praktikum;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethods extends RestAssured {

    private final String ORDER_PATH = "api/orders/";
    private String accessToken;


    public ValidatableResponse create(Order order, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse getUserOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse getAllOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDER_PATH + "all")
                .then()
                .log().all();
    }

}

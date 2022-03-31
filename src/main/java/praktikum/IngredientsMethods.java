package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsMethods extends RestAssured {

    public String INGREDIENTS_PATH = "api/ingredients";

    @Step("Get ingredients")
    public ValidatableResponse get() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .log().all();
    }
}

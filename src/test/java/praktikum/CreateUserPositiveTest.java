package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CreateUserPositiveTest {

    private UserMethods userMethods;
    private String accessToken;
    private BasePage basePage;


    @Before
    public void setup() {
        userMethods = new UserMethods();
        basePage = new BasePage();
    }


    @Step("Get user accessToken")
    public void getUserAccessToken(ValidatableResponse response) {
        // Запись accessToken пользователя для последующего удаления
        accessToken = response.extract().path("accessToken");
    }

    @After
    public void tearDown() {
        // Удаление пользователя
        basePage.deleteUser(accessToken, userMethods);
    }


    @Test
    @Description("User creation test")
    public void testCreateUserPositive() {

        // Создание пользователя
        User user = User.getRandom();
        ValidatableResponse response = userMethods.createUser(user);

        // Проверка ответа
        response.assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true),
                        "user.name", equalTo(user.name), "user.email", equalTo((user.email).toLowerCase()),
                        "accessToken", notNullValue(), "refreshToken", notNullValue());

        // Запись токена пользователя для последующего удаления
        getUserAccessToken(response);
    }
}

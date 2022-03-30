package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserPositiveTest {

    private UserMethods userMethods;
    private String accessToken;
    private BasePage basePage;

    @Before
    public void setup() {
        userMethods = new UserMethods();
        basePage = new BasePage();
    }

    @After
    public void tearDown() {
        // Удаление пользователя
        basePage.deleteUser(accessToken, userMethods);
    }


    @Test
    @Description("User login test")
    public void testLoginUserPositive() {

        // Создание пользователя, получение токена
        User user = User.getRandom();
        accessToken = basePage.createUser(user, userMethods);

        // Авторизация пользователя
        ValidatableResponse response = userMethods.loginUser(new UserCredentials(user.password, user.email));

        // Проверка ответа
        response.assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true),
                        "user.name", equalTo(user.name), "user.email", equalTo((user.email).toLowerCase()),
                        "accessToken", notNullValue(), "refreshToken", notNullValue());
    }
}

package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class LoginUserParameterizedNegativeTest {

    private final boolean success;
    private final String message;
    private final UserCredentials userCredentials;
    private UserMethods userMethods;
    private String accessToken;
    private BasePage basePage;

    public LoginUserParameterizedNegativeTest(UserCredentials userCredentials, boolean success, String message) {
        this.userCredentials = userCredentials;
        this.success = success;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {UserCredentials.getUserCredentials("", "apitest@yandex.ru"), false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("12345678", ""), false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials(null, "apitest@yandex.ru"), false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("12345678", null), false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("87654321", "apitest@yandex.ru"), false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("12345678", "apitest001@yandex.ru"), false, "email or password are incorrect"}
        };
    }

    @Before
    public void setup() {
        userMethods = new UserMethods();
        basePage = new BasePage();

        // создание пользователя, получение токена
        accessToken = basePage.createUser(User.getRandomWithoutPassAndEmail("12345678", "apitest@yandex.ru"), userMethods);
    }

    @After
    public void tearDown() {
        // Удаление пользователя
        basePage.deleteUser(accessToken, userMethods);
    }


    @Test
    @DisplayName("Check login of user: not enough data to login")
    @Description("It is checked that it is impossible to user login without email or password")
    public void testGetResponse() {

        // Авторизация пользователя
        ValidatableResponse response = userMethods.loginUser(userCredentials);

        // Проверка ответа
        response.assertThat().statusCode(401).and().body("success", equalTo(success), "message", equalTo(message));
    }
}

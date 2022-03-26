package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class LoginUserNegativeTest {

    private final int statusCode;
    private final boolean success;
    private final String message;
    private UserMethods userMethods;
    private final UserCredentials userCredentials;
    private String accessToken;

    public LoginUserNegativeTest(UserCredentials userCredentials, int statusCode, boolean success, String message) {
        this.userCredentials = userCredentials;
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {UserCredentials.getUserCredentials("", "apitest@yandex.ru"), 401, false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("12345678", ""), 401, false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials(null, "apitest@yandex.ru"), 401, false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("12345678", null), 401, false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("87654321", "apitest@yandex.ru"), 401, false, "email or password are incorrect"},
                {UserCredentials.getUserCredentials("12345678", "apitest001@yandex.ru"), 401, false, "email or password are incorrect"}
        };
    }

    @Step("Before test: send POST request to /api/auth/register - to create user")
    public void createUser() {

        // Создание пользователя
        accessToken = userMethods.create(User.getRandomWithoutPassAndEmail("12345678", "apitest@yandex.ru"))
                .assertThat().statusCode(200)
                .and()
                .extract().path("accessToken");
    }

    @Before
    public void setup() {
        userMethods = new UserMethods();

        // Создание пользователя
        createUser();
    }

    @After
    @Step("After test: send DELETE request to api/auth/user - to delete user")
    public void tearDown() {
        if (accessToken != null) {
            ValidatableResponse response = userMethods.delete(accessToken.substring(7));
            if (response.extract().statusCode() == 202) {
                System.out.println("\nuser is deleted\n");
            } else {
                System.out.println("\nuser was not be deleted\n");
            }
        }
    }

    @Test
    @DisplayName("Check login of user: not enough data to login")
    @Description("It is checked that it is impossible to user login without email or password")
    public void testGetResponse() {


        // Авторизация пользователя
        ValidatableResponse response = userMethods.login(userCredentials);

        // Проверка ответа
        response.assertThat().statusCode(statusCode).and().body("success", equalTo(success), "message", equalTo(message));
    }

}

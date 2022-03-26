package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserPositiveTest {

    private UserMethods userMethods;
    private String accessToken;

    @Before
    public void setup() {
        userMethods = new UserMethods();
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

    @Step("Before test: send POST request to /api/register - to create user")
    public void createUser(User user) {

        // Создание пользователя
        accessToken = userMethods.create(user).assertThat().statusCode(200).and().extract().path("accessToken");
    }

    @Test
    @Description("User login test")
    public void testLoginUserPositive() {

        User user = User.getRandom();

        // Создание пользователя
        createUser(user);

        // Авторизация пользователя
        ValidatableResponse response = userMethods.login(new UserCredentials(user.password, user.email));


        // Проверка ответа
        response.assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true),
                        "user.name", equalTo(user.name), "user.email", equalTo((user.email).toLowerCase()),
                        "accessToken", notNullValue(), "refreshToken", notNullValue());

    }

}

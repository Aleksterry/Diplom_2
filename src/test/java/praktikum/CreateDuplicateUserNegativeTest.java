package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateDuplicateUserNegativeTest extends BasePage {

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
    @DisplayName("Check user creation: this user is already in use")
    @Description("It is checked that it is impossible to create a user with the same credentials")
    public void testUserDuplicateNegative() {

        // Создание пользователя, получение токена
        User user = User.getRandom();
        accessToken = basePage.createUser(user, userMethods);

        // Создание пользователя с теми же данными
        ValidatableResponse response = userMethods.createUser(user);

        // Проверка ответа
        response.assertThat().statusCode(403)
                .and()
                .body("success", equalTo(false), "message", equalTo("User already exists"));
    }
}

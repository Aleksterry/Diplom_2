package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


public class EditUserPositiveTest extends BasePage {

    private UserMethods userMethods;
    private String accessToken;

    @Before
    public void setup() {
        userMethods = new UserMethods();
    }

    @After
    public void tearDown() {
        // Удаление пользователя
        deleteUser(accessToken, userMethods);
    }


    @Test
    @Description("User edit test")
    public void testEditUserPositive() {

        // Создание пользователя, получение токена
        User user = User.getRandom();
        accessToken = createUser(user, userMethods);

        // Генерация данных для новых данных пользователя
        User editUser = User.getRandom();

        // Изменение пользователя
        ValidatableResponse response = userMethods.editUser(editUser, accessToken.substring(7));


        // Проверка ответа
        response.assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true),
                        "user.name", equalTo(editUser.name), "user.email", equalTo((editUser.email).toLowerCase()));
    }
}

package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


public class EditUserPositiveTest {

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
    @Description("User edit test")
    public void testEditUserPositive() {

        // Создание пользователя, получение токена
        User user = User.getRandom();
        accessToken = basePage.createUser(user, userMethods);

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

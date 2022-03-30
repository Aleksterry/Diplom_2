package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class EditUserWithoutTokenNegativeTest {

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
        ValidatableResponse response = userMethods.editUser(editUser, "");


        // Проверка ответа
        response.assertThat().statusCode(401)
                .and()
                .body("success", equalTo(false), "message", equalTo("You should be authorised"));
    }
}

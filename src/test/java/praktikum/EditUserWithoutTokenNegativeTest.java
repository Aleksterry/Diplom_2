package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class EditUserWithoutTokenNegativeTest {

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
    @Description("User edit test")
    public void testEditUserPositive() {

        // Генерация данных для нового пользователя
        User user = User.getRandom();

        // Создание пользователя
        createUser(user);

        // Генерация данных для новых данных пользователя
        User editUser = User.getRandom();

        // Изменение пользователя
        ValidatableResponse response = userMethods.edit(editUser, "");


        // Проверка ответа
        response.assertThat().statusCode(401)
                .and()
                .body("success", equalTo(false), "message", equalTo("You should be authorised"));

    }

}

package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;


@RunWith(Parameterized.class)
public class CreateUserParameterizedNegativeTest {

    private final User user;
    private final int statusCode;
    private final boolean success;
    private final String message;
    private UserMethods userMethods;

    public CreateUserParameterizedNegativeTest(User user, int statusCode, boolean success, String message) {
        this.user = user;
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {User.getRandomWithoutName(null), 403, false, "Email, password and name are required fields"},
                {User.getRandomWithoutName(""), 403, false, "Email, password and name are required fields"},
                {User.getRandomWithoutPass(null), 403, false, "Email, password and name are required fields"},
                {User.getRandomWithoutPass(""), 403, false, "Email, password and name are required fields"},
                {User.getRandomWithoutEmail(null), 403, false, "Email, password and name are required fields"},
                {User.getRandomWithoutEmail(""), 403, false, "Email, password and name are required fields"}
        };
    }

    @Before
    public void setup() {
        userMethods = new UserMethods();
    }

    @Test
    @DisplayName("Check creation of user: not enough data to create")
    @Description("It is checked that it is impossible to create a courier without login or password")
    public void testGetResponse() {

        // Создание пользователя
        ValidatableResponse response = userMethods.create(user);

        // Проверка ответа
        response.assertThat().statusCode(statusCode).and().body("success", equalTo(success), "message", equalTo(message));
    }

}

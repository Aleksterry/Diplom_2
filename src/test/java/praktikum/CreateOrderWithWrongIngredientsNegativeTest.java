package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class CreateOrderWithWrongIngredientsNegativeTest {

    private OrderMethods orderMethods;
    private UserMethods userMethods;
    private String accessToken;


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
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
    public void createUser() {

        // Создание пользователя
        accessToken = userMethods.create(User.getRandom()).assertThat().statusCode(200).and().extract().path("accessToken");
    }


    @Test
    @Description("Order creation with wrong ingredients test")
    public void testCreateOrderWithWrongIngredients() {

        // Создание пользователя
        createUser();

        // Формирование тела запроса заказа
        Order order = new Order(List.of("111abc", "222def", "333ijk"));

        // Создание заказа
        ValidatableResponse response = orderMethods.create(order, accessToken.substring(7));

        // Проверка ответа
        response.assertThat().statusCode(500);
    }
}

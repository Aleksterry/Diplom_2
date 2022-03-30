package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class CreateOrderWithWrongIngredientsNegativeTest {

    private OrderMethods orderMethods;
    private UserMethods userMethods;
    private String accessToken;
    private BasePage basePage;


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
        userMethods = new UserMethods();
        basePage = new BasePage();
    }

    @After
    public void tearDown() {
        // Удаление пользователя
        basePage.deleteUser(accessToken, userMethods);
    }


    @Test
    @Description("Order creation with wrong ingredients test")
    public void testCreateOrderWithWrongIngredients() {

        // Создание пользователя, получение токена
        User user = User.getRandom();
        accessToken = basePage.createUser(user, userMethods);

        // Формирование тела запроса заказа
        Order order = new Order(List.of("111abc", "222def", "333ijk"));

        // Создание заказа
        ValidatableResponse response = orderMethods.createOrder(order, accessToken.substring(7));

        // Проверка ответа
        response.assertThat().statusCode(500);
    }
}

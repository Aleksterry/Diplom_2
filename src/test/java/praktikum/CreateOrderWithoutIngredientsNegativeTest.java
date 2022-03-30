package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


public class CreateOrderWithoutIngredientsNegativeTest extends BasePage {

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
    @Description("Order creation without ingredients test")
    public void testCreateOrderWithoutIngredientsNegative() {

        // Создание пользователя, получение токена
        User user = User.getRandom();
        accessToken = basePage.createUser(user, userMethods);

        // Формирование тела запроса заказа
        Order order = new Order();

        // Создание заказа
        ValidatableResponse response = orderMethods.createOrder(order, accessToken.substring(7));

        // Проверка ответа
        response.assertThat().statusCode(400)
                .and()
                .body("success", equalTo(false), "message", equalTo("Ingredient ids must be provided"));
    }
}

package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetUserOrdersWithTokenTest extends BasePage {

    private OrderMethods orderMethods;
    private UserMethods userMethods;
    private Ingredients ingredients;
    private String accessToken;


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
        userMethods = new UserMethods();
        ingredients = new Ingredients();

        // Создание пользователя, получение токена
        User user = User.getRandom();
        accessToken = createUser(user, userMethods);
    }


    @Step("Create order")
    public void createOrder() {
        // Формирование тела запроса заказа
        Order order = new Order(ingredients.getIngredients());
        // Создание заказа
        orderMethods.createOrder(order, accessToken.substring(7)).assertThat().statusCode(200);
    }

    @After
    public void tearDown() {
        // Удаление пользователя
        deleteUser(accessToken, userMethods);
    }


    @Test
    @Description("User order list test")
    public void testCreateOrderPositive() {

        // Создание заказа
        createOrder();

        // Получение списка заказов пользователя
        ValidatableResponse response = orderMethods.getUserOrders(accessToken.substring(7));

        // Проверка ответа
        response.assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true), "orders", notNullValue(),
                        "orders._id", notNullValue(),
                        "orders.ingredients", notNullValue(),
                        "orders.status", notNullValue(),
                        "orders.name", notNullValue(),
                        "orders.createdAt", notNullValue(),
                        "orders.updatedAt", notNullValue(),
                        "orders.number", notNullValue(),
                        "total", notNullValue(),
                        "totalToday", notNullValue());
    }
}

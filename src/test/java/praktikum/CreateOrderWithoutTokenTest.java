package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderWithoutTokenTest extends BasePage {

    private OrderMethods orderMethods;
    private Ingredients ingredients;


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
        ingredients = new Ingredients();
    }


    @Test
    @Description("Order creation test")
    public void testCreateOrderPositive() {

        // Формирование тела запроса заказа
        Order order = new Order(ingredients.getIngredients());

        // Создание заказа
        ValidatableResponse response = orderMethods.createOrder(order, "");

        // Проверка ответа
        response.assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true), "name", notNullValue(), "order.number", notNullValue(),
                        "order.ingredients", nullValue(),
                        "order._id", nullValue(),
                        "order.owner", nullValue(),
                        "order.status", nullValue(),
                        "order.name", nullValue(),
                        "order.createdAt", nullValue(),
                        "order.updatedAt", nullValue(),
                        "order.price", nullValue());
    }
}

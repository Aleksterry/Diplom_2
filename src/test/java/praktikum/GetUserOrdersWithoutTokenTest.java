package praktikum;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetUserOrdersWithoutTokenTest {

    private OrderMethods orderMethods;


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
    }


    @Test
    @Description("User order list test")
    public void testCreateOrderPositive() {

        // Получение списка заказов пользователя
        ValidatableResponse response = orderMethods.getUserOrders("");

        // Проверка ответа
        response.assertThat().statusCode(401)
                .and()
                .body("success", equalTo(false),"message", equalTo("You should be authorised"));

    }
}

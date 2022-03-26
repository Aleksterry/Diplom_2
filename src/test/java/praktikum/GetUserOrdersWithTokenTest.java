package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetUserOrdersWithTokenTest {

    private OrderMethods orderMethods;
    private UserMethods userMethods;
    private GetIngredients getIngredients;
    private String accessToken;


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
        userMethods = new UserMethods();
        getIngredients = new GetIngredients();
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

    @Step("Before test: send POST request to api/orders - to create order")
    public void createOrder() {

        // Формирование тела запроса заказа
        Order order = new Order(getIngredients.getIngredients());

        // Создание заказа
        ValidatableResponse response = orderMethods.create(order, accessToken.substring(7)).assertThat().statusCode(200);
    }


    @Test
    @Description("User order list test")
    public void testCreateOrderPositive() {

        // Создание пользователя
        createUser();

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

        // Запись данных заказа для последующего удаления
    }
}

package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CreateOrderWithTokenTest {

    private OrderMethods orderMethods;
    private GetIngredients getIngredients;
    private UserMethods userMethods;
    private String accessToken;


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
        getIngredients = new GetIngredients();
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
    @Description("Order creation test")
    public void testCreateOrderPositive() {

        // Создание пользователя
        createUser();

        // Формирование тела запроса заказа
        Order order = new Order(getIngredients.getIngredients());

        // Создание заказа
        ValidatableResponse response = orderMethods.create(order, accessToken.substring(7));

        // Проверка ответа
        response.assertThat().statusCode(200)
                .and()
                .body("success", equalTo(true), "name", notNullValue(), "order.number", notNullValue(),
                        "order.ingredients", notNullValue(),
                        "order.ingredients._id", notNullValue(),
                        "order.ingredients.name", notNullValue(),
                        "order.ingredients.type", notNullValue(),
                        "order.ingredients.proteins", notNullValue(),
                        "order.ingredients.fat", notNullValue(),
                        "order.ingredients.carbohydrates", notNullValue(),
                        "order.ingredients.calories", notNullValue(),
                        "order.ingredients.price", notNullValue(),
                        "order.ingredients.image", notNullValue(),
                        "order.ingredients.image_mobile", notNullValue(),
                        "order.ingredients.image_large", notNullValue(),
                        "order.ingredients.__v", notNullValue(),
                        "order._id", notNullValue(),
                        "order.owner", notNullValue(),
                        "order.owner.name", notNullValue(),
                        "order.owner.email", notNullValue(),
                        "order.owner.createdAt", notNullValue(),
                        "order.owner.updatedAt", notNullValue(),
                        "order.status", notNullValue(),
                        "order.name", notNullValue(),
                        "order.createdAt", notNullValue(),
                        "order.updatedAt", notNullValue(),
                        "order.price", notNullValue());

        // Запись данных заказа для последующего удаления
    }

}

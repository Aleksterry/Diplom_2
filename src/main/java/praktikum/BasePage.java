package praktikum;

import io.restassured.response.ValidatableResponse;

public class BasePage {

    public UserMethods userMethods;
    public String accessToken;

    public void deleteUser(String accessToken, UserMethods userMethods) {
        if (accessToken != null) {
            ValidatableResponse response = userMethods.deleteUser(accessToken.substring(7));
            if (response.extract().statusCode() == 202) {
                System.out.println("\nuser is deleted\n");
            } else {
                System.out.println("\nuser was not be deleted\n");
            }
        }
    }

    public String createUser(User user, UserMethods userMethods) {
        // Создание пользователя
        return accessToken = userMethods.createUser(user).assertThat().statusCode(200).and().extract().path("accessToken");
    }
}

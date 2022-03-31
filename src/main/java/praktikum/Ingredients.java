package praktikum;

import com.github.javafaker.Faker;
import io.restassured.response.ValidatableResponse;

import java.util.List;

public class Ingredients {

    public List<String> ingredients;
    public List<String> ingredientsRandom;

    public List<String> getIngredients() {

        IngredientsMethods ingredientsMethods = new IngredientsMethods();

        //Запрос списка ингридиентов
        ValidatableResponse response = ingredientsMethods.get();

        //Извлечение id ингридиентов
        ingredients = response.extract().path("data._id");

        Faker faker = new Faker();

        //Получение рандомных индексов списка id ингридиентов
        int element1 = faker.number().numberBetween(0, ingredients.size());
        int element2 = faker.number().numberBetween(0, ingredients.size());
        int element3 = faker.number().numberBetween(0, ingredients.size());

        //Формирование списка рандомных id ингридиентов
        return ingredientsRandom = List.of(ingredients.get(element1), ingredients.get(element2), ingredients.get(element3));
    }
}

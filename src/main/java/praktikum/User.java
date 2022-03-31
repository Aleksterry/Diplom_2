package praktikum;


import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class User {

    public String name;
    public String password;
    public String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public static User getRandom() {
        Faker faker = new Faker();

        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String name = faker.name().fullName();
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new User(name, password, email);
    }

    public static User getRandomWithoutName(String name) {
        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new User(name, password, email);
    }

    public static User getRandomWithoutPass(String password) {
        Faker faker = new Faker();

        final String email = RandomStringUtils.randomAlphabetic(10) + "@yandex.ru";
        final String name = faker.name().fullName();
        return new User(name, password, email);
    }

    public static User getRandomWithoutEmail(String email) {
        Faker faker = new Faker();

        final String name = faker.name().fullName();
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new User(name, password, email);
    }

    public static User getRandomWithoutPassAndEmail(String password, String email) {
        Faker faker = new Faker();
        final String name = faker.name().fullName();
        return new User(name, password, email);
    }
}

package praktikum;

public class UserCredentials {

    public String password;
    public String email;

    public UserCredentials(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public UserCredentials() {
    }

    public static UserCredentials getUserCredentials(User user) {
        return new UserCredentials(user.password, user.email);
    }

    public static UserCredentials getUserCredentials(String password, String email) {
        return new UserCredentials(password, email);
    }

}

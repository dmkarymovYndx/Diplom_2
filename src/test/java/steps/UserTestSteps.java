package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.yandex.classes.user.User;

import static io.restassured.RestAssured.given;
import static ru.praktikum.yandex.constants.Endpoints.*;

public class UserTestSteps extends CommonSteps {

    // обновление информации о пользователе с авторизацией
    @Step
    protected Response userChangeData(String email, String password, String newEmail, String newName) {

        Response loginResponse = userLogin(email, password);
        String accessToken = loginResponse.jsonPath().getString("accessToken");

        User newUserData = new User(newEmail, newName);

        return given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(newUserData)
                .when()
                .patch(USER_AUTH);

    }

    // обновление информации о пользователе без авторизации
    @Step
    protected Response userChangeDataUnauthorized(String email, String password, String newEmail, String newName) {

        userLogin(email, password);
        User newUserData = new User(newEmail, newName);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(newUserData)
                .when()
                .patch(USER_AUTH);
    }

}

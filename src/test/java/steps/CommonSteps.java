package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.yandex.classes.user.UserDataClient;

import static io.restassured.RestAssured.given;
import static ru.praktikum.yandex.constants.Endpoints.*;

public class CommonSteps {

    // регистрация пользователя
    @Step
    protected Response userRegister(String email, String password, String name) {

        UserDataClient user = new UserDataClient(email, password, name);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(USER_REGISTER);

    }

    // авторизация пользователя
    @Step
    protected Response userLogin(String email, String password) {

        UserDataClient user = new UserDataClient(email, password);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(USER_LOGIN);

    }

    // удаление пользователя
    @Step
    protected void userDelete(String email, String password) {

        Response loginResponse = userLogin(email, password);
        String accessToken = loginResponse.jsonPath().getString("accessToken");

        given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_AUTH);

    }

}

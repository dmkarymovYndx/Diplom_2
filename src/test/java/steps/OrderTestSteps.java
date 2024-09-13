package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.yandex.classes.order.OrderDataClient;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static ru.praktikum.yandex.constants.Endpoints.ORDERS;

public class OrderTestSteps extends CommonSteps {

    // создание заказа с авторизацией
    @Step
    protected Response createOrderAuthorized(String email, String password, ArrayList<String> ingredients) {

        Response loginResponse = userLogin(email, password);
        String accessToken = loginResponse.jsonPath().getString("accessToken");

        OrderDataClient newOrder = new OrderDataClient(ingredients);

        return given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(newOrder)
                .when()
                .post(ORDERS);

    }

    // создание заказа без авторизации
    @Step
    protected Response createOrderUnauthorized(ArrayList<String> ingredients) {

        OrderDataClient newOrder = new OrderDataClient(ingredients);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(newOrder)
                .when()
                .post(ORDERS);

    }

    // получение заказов конкретного пользователя без авторизации
    @Step
    protected Response getUserOrdersListUnauthorized() {

        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS);

    }

    // получение заказов конкретного пользователя с авторизацией
    @Step
    protected Response getUserOrdersListAuthorized(String email, String password) {

        Response loginResponse = userLogin(email, password);
        String accessToken = loginResponse.jsonPath().getString("accessToken");

        return given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS);

    }

}

package order.getlist;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.OrderTestSteps;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.*;

public class GetOrdersListUnauthorizedTests extends OrderTestSteps {

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if getting orders list unauthorized returns 401 status code")
    public void getOrdersListUnauthorizedReturns401() {
        Response response = getUserOrdersListUnauthorized();
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 401, statusCode);
    }

    @Test
    @DisplayName("Check if getting orders list unauthorized returns correct data in the response body")
    public void getOrdersListUnauthorizedReturnsCorrectData() {
        Response response = getUserOrdersListUnauthorized();
        boolean isSuccess = response.jsonPath().getBoolean("success");
        String message = response.jsonPath().getString("message");
        assertFalse("Ошибка: неверный статус success в теле ответа", isSuccess);
        assertEquals("Ошибка: неверное сообщение в теле ответа", "You should be authorised", message);
    }

}

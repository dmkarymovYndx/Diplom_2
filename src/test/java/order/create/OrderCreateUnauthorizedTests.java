package order.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.OrderTestSteps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.*;
import static ru.praktikum.yandex.constants.OrderTestsData.*;

public class OrderCreateUnauthorizedTests extends OrderTestSteps {

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if unauthorized order creation returns 200 status code")
    public void orderCreationUnauthorizedReturns200() {
        Response response = createOrderUnauthorized(new ArrayList<String>(Arrays.asList(BUN, SAUCE, MAIN)));
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 200, statusCode);
    }

    @Test
    @DisplayName("Check if unauthorized order creation returns correct data in the response body")
    public void orderCreationUnauthorizedReturnsCorrectData() {
        Response response = createOrderUnauthorized(new ArrayList<String>(Arrays.asList(BUN, SAUCE, MAIN)));
        boolean isSuccess = response.jsonPath().getBoolean("success");
        String orderName = response.jsonPath().getString("name");
        String orderNumber = response.jsonPath().getString("order.number");
        assertTrue("Ошибка: неверный статус success в теле ответа", isSuccess);
        assertNotNull("Ошибка: отсутствует название заказа", orderName);
        assertNotNull("Ошибка: отсутствует номер заказа", orderNumber);
    }

    @Test
    @DisplayName("Check if order creation without ingredients returns 400 status code")
    public void orderCreationWithoutIngredientsReturns400() {
        Response response = createOrderUnauthorized(new ArrayList<String>(List.of()));
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 400, statusCode);
    }

    @Test
    @DisplayName("Check if order creation without ingredients returns correct data in the response body")
    public void orderCreationWithoutIngredientsReturnsCorrectData() {
        Response response = createOrderUnauthorized(new ArrayList<String>(List.of()));
        boolean isSuccess = response.jsonPath().getBoolean("success");
        String message = response.jsonPath().getString("message");
        assertFalse("Ошибка: неверный статус success в теле ответа", isSuccess);
        assertEquals("Ошибка: неверное сообщение в теле ответа", "Ingredient ids must be provided", message);
    }

    @Test
    @DisplayName("Check if order creation with wrong ingredient hash returns 500 status code")
    public void orderCreationWithWrongHashReturns500() {
        Response response = createOrderUnauthorized(new ArrayList<String>(List.of(WRONG_HASH)));
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 500, statusCode);
    }

}

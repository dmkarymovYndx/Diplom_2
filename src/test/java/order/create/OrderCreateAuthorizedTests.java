package order.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.OrderTestSteps;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.*;
import static ru.praktikum.yandex.constants.OrderTestsData.*;
import static ru.praktikum.yandex.constants.UserTestsData.*;

public class OrderCreateAuthorizedTests extends OrderTestSteps {

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if authorized order creation returns 200 status code")
    public void orderCreationAuthorizedReturns200() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = createOrderAuthorized(EMAIL, PASSWORD, new ArrayList<String>(Arrays.asList(BUN, SAUCE, MAIN)));
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 200, statusCode);
    }

    @Test
    @DisplayName("Check if authorized order creation returns correct data in the response body")
    public void orderCreationAuthorizedReturnsCorrectData() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = createOrderAuthorized(EMAIL, PASSWORD, new ArrayList<String>(Arrays.asList(BUN, SAUCE, MAIN)));
        boolean isSuccess = response.jsonPath().getBoolean("success");
        String orderName = response.jsonPath().getString("name");
        String orderNumber = response.jsonPath().getString("order.number");
        assertTrue("Ошибка: неверный статус success в теле ответа", isSuccess);
        assertNotNull("Ошибка: отсутствует название заказа", orderName);
        assertNotNull("Ошибка: отсутствует номер заказа", orderNumber);
    }

    @After
    public void cleanUp() {
        userDelete(EMAIL, PASSWORD);
    }

}

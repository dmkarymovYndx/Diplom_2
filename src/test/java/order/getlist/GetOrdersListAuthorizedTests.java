package order.getlist;

import steps.OrderTestSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.*;
import static ru.praktikum.yandex.constants.UserTestsData.*;

public class GetOrdersListAuthorizedTests extends OrderTestSteps {

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if getting orders list authorized returns 200 status code")
    public void getOrdersListAuthorizedReturns401() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = getUserOrdersListAuthorized(EMAIL, PASSWORD);
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 200, statusCode);
    }

    @Test
    @DisplayName("Check if getting orders list authorized returns correct data in the response body")
    public void getOrdersListAuthorizedReturnsCorrectData() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = getUserOrdersListAuthorized(EMAIL, PASSWORD);
        boolean isSuccess = response.jsonPath().getBoolean("success");
        assertTrue("Ошибка: неверный статус success в теле ответа", isSuccess);
    }

    @After
    public void cleanUp() {
        userDelete(EMAIL, PASSWORD);
    }

}

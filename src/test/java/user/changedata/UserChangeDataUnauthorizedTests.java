package user.changedata;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserTestSteps;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.*;
import static ru.praktikum.yandex.constants.UserTestsData.*;

public class UserChangeDataUnauthorizedTests extends UserTestSteps {

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if unauthorized changing User data returns 401 status code")
    public void userChangeDataUnauthorizedReturns401() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userChangeDataUnauthorized(EMAIL, PASSWORD, EMAIL_2, NAME_2);
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 401, statusCode);
    }

    @Test
    @DisplayName("Check if unauthorized changing User data returns correct data in the response body")
    public void userChangeDataUnauthorizedReturnsCorrectData() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userChangeDataUnauthorized(EMAIL, PASSWORD, EMAIL_2, NAME_2);
        boolean responseSuccess = response.jsonPath().getBoolean("success");
        String responseMessage = response.jsonPath().getString("message");
        assertFalse("Ошибка: неверный статус success в теле ответа", responseSuccess);
        assertEquals("Ошибка: неверное сообщение в теле ответа", "You should be authorised", responseMessage);
    }

    @After
    public void cleanUp() {
        userDelete(EMAIL, PASSWORD);
    }

}

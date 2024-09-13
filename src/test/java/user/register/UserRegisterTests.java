package user.register;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.yandex.classes.user.UserDataServer;
import steps.UserTestSteps;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.STELLARBURGERS_URL;
import static ru.praktikum.yandex.constants.UserTestsData.*;

public class UserRegisterTests extends UserTestSteps {

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if new User registration returns 200 OK status code")
    public void newUserRegisterReturns200() {
        Response response = userRegister(EMAIL, PASSWORD, NAME);
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 200, statusCode);
    }

    @Test
    @DisplayName("Check if new User registration returns correct data in the response body")
    public void newUserRegisterReturnsCorrectData() {
        UserDataServer responseData = userRegister(EMAIL, PASSWORD, NAME).as(UserDataServer.class);
        assertTrue("Ошибка: неверный статус success в теле ответа", responseData.isSuccess());
        assertEquals("Ошибка: имя пользователя не соответствует зарегистрированному", NAME, responseData.getUser().getName());
        assertEquals("Ошибка: email пользователя не соответствует зарегистрированному", EMAIL, responseData.getUser().getEmail());
        assertNotNull("Ошибка: отсутствует accessToken", responseData.getAccessToken());
        assertNotNull("Ошибка: отсутствует refreshToken", responseData.getRefreshToken());
    }

    @Test
    @DisplayName("Check if User registration with existing creds returns 403 status code")
    public void existingUserRegistrationReturns403() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userRegister(EMAIL, PASSWORD, NAME);
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 403, statusCode);
    }

    @Test
    @DisplayName("Check if User registration with existing creds returns correct data in the response body")
    public void existingUserRegisterReturnsCorrectData() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userRegister(EMAIL, PASSWORD, NAME);
        boolean responseSuccess = response.jsonPath().getBoolean("success");
        String responseMessage = response.jsonPath().getString("message");
        assertFalse("Ошибка: неверный статус success в теле ответа", responseSuccess);
        assertEquals("Ошибка: неверное сообщение в теле ответа", "User already exists", responseMessage);
    }

    @After
    public void cleanUp() {
        userDelete(EMAIL, PASSWORD);
    }


}

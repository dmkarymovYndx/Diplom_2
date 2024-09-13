package user.login;

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

public class UserLoginTests extends UserTestSteps {

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check is existing User login returns 200 status code")
    public void existingUserLoginReturns200 () {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userLogin(EMAIL, PASSWORD);
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 200, statusCode);
    }

    @Test
    @DisplayName("Check if existing User login returns correct data in the response body")
    public void existingUserLoginReturnsCorrectData() {
        userRegister(EMAIL, PASSWORD, NAME);
        UserDataServer responseData = userLogin(EMAIL, PASSWORD).as(UserDataServer.class);
        assertTrue("Ошибка: неверный статус success в теле ответа", responseData.isSuccess());
        assertEquals("Ошибка: имя пользователя не соответствует зарегистрированному", NAME, responseData.getUser().getName());
        assertEquals("Ошибка: email пользователя не соответствует зарегистрированному", EMAIL, responseData.getUser().getEmail());
        assertNotNull("Ошибка: отсутствует accessToken", responseData.getAccessToken());
        assertNotNull("Ошибка: отсутствует refreshToken", responseData.getRefreshToken());
    }

    @After
    public void cleanUp() {
        userDelete(EMAIL, PASSWORD);
    }

}

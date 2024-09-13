package user.login;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.UserTestSteps;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.*;
import static ru.praktikum.yandex.constants.UserTestsData.*;

@RunWith(Parameterized.class)
public class UserLoginIncorrectFieldsTests extends UserTestSteps {

    private final String email;
    private final String password;

    public UserLoginIncorrectFieldsTests(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {EMAIL, PASSWORD_2},
                {EMAIL, ""},
                {EMAIL, null},
                {EMAIL_2, PASSWORD},
                {"", PASSWORD},
                {null, PASSWORD}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if existing User login with an incorrect field returns 401 status code")
    public void userLoginIncorrectFieldReturns401() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userLogin(email, password);
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 401, statusCode);
    }

    @Test
    @DisplayName("Check if existing User login with an incorrect field returns correct data in the response body")
    public void userLoginIncorrectFieldReturnsCorrectData() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userLogin(email, password);
        boolean responseSuccess = response.jsonPath().getBoolean("success");
        String responseMessage = response.jsonPath().getString("message");
        assertFalse("Ошибка: неверный статус success в теле ответа", responseSuccess);
        assertEquals("Ошибка: неверное сообщение в теле ответа", "email or password are incorrect", responseMessage);
    }

    @After
    public void cleanUp() {
        userDelete(EMAIL, PASSWORD);
    }

}

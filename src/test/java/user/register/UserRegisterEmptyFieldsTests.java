package user.register;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.UserTestSteps;

import static org.junit.Assert.*;
import static ru.praktikum.yandex.constants.Endpoints.*;
import static ru.praktikum.yandex.constants.UserTestsData.*;

// toDo: Подумать, как присобачить After, который срабатывает только при наличии пользователя

@RunWith(Parameterized.class)
public class UserRegisterEmptyFieldsTests extends UserTestSteps {

    private final String email;
    private final String password;
    private final String name;

    public UserRegisterEmptyFieldsTests(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {"", PASSWORD, NAME},
                {null, PASSWORD, NAME},
                {EMAIL, "", NAME},
                {EMAIL, null, NAME},
                {EMAIL, PASSWORD, ""},
                {EMAIL, PASSWORD, null}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if new User registration with an empty field returns 403 status code")
    public void newUserRegisterWithEmptyFieldReturns403() {
        Response response = userRegister(email, password, name);
        int statusCode = response.getStatusCode();
        assertEquals("Ошибка: неверный статус-код", 403, statusCode);
    }

    @Test
    @DisplayName("Check if new User registration with an empty field returns correct data in the response body")
    public void newUserRegisterWithEmptyFieldReturnsCorrectData() {
        Response response = userRegister(email, password, name);
        boolean responseSuccess = response.jsonPath().getBoolean("success");
        String responseMessage = response.jsonPath().getString("message");
        assertFalse("Ошибка: неверный статус success в теле ответа", responseSuccess);
        assertEquals("Ошибка: неверное сообщение в теле ответа", "Email, password and name are required fields", responseMessage);
    }

}

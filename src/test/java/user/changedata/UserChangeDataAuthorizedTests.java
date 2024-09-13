package user.changedata;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.yandex.classes.user.UserDataServer;
import steps.UserTestSteps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.praktikum.yandex.constants.Endpoints.*;
import static ru.praktikum.yandex.constants.UserTestsData.*;

@RunWith(Parameterized.class)
public class UserChangeDataAuthorizedTests extends UserTestSteps {

    private final String newEmail;
    private final String newName;
    private final String expectedEmail;
    private final String expectedName;

    String changedEmail;

    public UserChangeDataAuthorizedTests(String newEmail, String newName, String expectedEmail, String expectedName) {
        this.newEmail = newEmail;
        this.newName = newName;
        this.expectedEmail = expectedEmail;
        this.expectedName = expectedName;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {EMAIL_2, null, EMAIL_2, NAME},
                {null, NAME_2, EMAIL, NAME_2},
                {EMAIL_2, NAME_2, EMAIL_2, NAME_2},
                {null, null, EMAIL, NAME}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = STELLARBURGERS_URL;
    }

    @Test
    @DisplayName("Check if changing User data returns 200 status code")
    public void userChangeDataReturns200() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userChangeData(EMAIL, PASSWORD, newEmail, newName);
        UserDataServer receivedUserData = response.as(UserDataServer.class);
        changedEmail = receivedUserData.getUser().getEmail();
        assertEquals("Ошибка: неверный статус-код", 200, response.getStatusCode());
    }

    @Test
    @DisplayName("Check if changing User data returns correct data in the response body")
    public void userChangeDataReturnsCorrectData() {
        userRegister(EMAIL, PASSWORD, NAME);
        Response response = userChangeData(EMAIL, PASSWORD, newEmail, newName);
        UserDataServer receivedUserData = response.as(UserDataServer.class);
        changedEmail = receivedUserData.getUser().getEmail();
        assertTrue("Ошибка: неверное значение параметра success", receivedUserData.isSuccess());
        assertEquals("Ошибка: значение поля email не соответствует ожидаемому", expectedEmail, receivedUserData.getUser().getEmail());
        assertEquals("Ошибка: значение поля name не соответствует ожидаемому", expectedName, receivedUserData.getUser().getName());
    }

    @After
    public void cleanUp() {
        userDelete(changedEmail, PASSWORD);
    }

}

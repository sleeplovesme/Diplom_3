package clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Login;
import models.LoginResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LoginClient {

    @Step("Проверка соответствия ожидаемого результата")
    public static void checkExpectedResult(Response response, int statusCode, boolean expectedResult) {
        response.then().assertThat().statusCode(statusCode).and().body("success", equalTo(expectedResult));
    }

    @Step("Создание объекта логин")
    public static Login createObjectLogin(String email, String password) {
        return new Login(email, password);
    }

    @Step("Десериализация ответа на логин пользователя")
    public static LoginResponse deserialization(Response responseLoginUser) {
        return responseLoginUser.as(LoginResponse.class);
    }

    @Step("Отправка POST запроса на /api/auth/login")
    public static Response sendPostRequestAuthLogin(Login login) {
        return given().contentType(ContentType.JSON).body(login)
                .post("https://stellarburgers.nomoreparties.site/api/auth/login");
    }

    @Step("Отправка DELETE запроса на /api/auth/user")
    public static Response sendDeleteRequestAuthUser(String accessToken) {
        return given().header("Authorization", accessToken)
                .delete("https://stellarburgers.nomoreparties.site/api/auth/user");
    }
}

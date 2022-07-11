package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Login;
import models.LoginResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegisterPage;

import java.util.Objects;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class RegistrationTests {

    private String randomString = RandomStringUtils.randomAlphanumeric(5);
    private String password = RandomStringUtils.randomNumeric(7);
    private String shortPassword = RandomStringUtils.randomNumeric(3);
    private String name = RandomStringUtils.randomAlphabetic(6);

    private String[] mailCompanies = new String[]{"yandex", "mail", "rambler"};
    private int randomMailCompany = new Random().nextInt(mailCompanies.length);
    private String email = randomString + "@" + mailCompanies[randomMailCompany] + ".ru";

    private final static String INVALID_PASSWORD = "Некорректный пароль";
    private final static boolean EXPECTED_RESULT_TRUE = true;

    final String URL = "https://stellarburgers.nomoreparties.site/";
    final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";

    MainPage mainPage;
    LoginPage loginPage;
    RegisterPage registerPage;

    @Before
    public void openMainPage() {
        Configuration.startMaximized = true;
        mainPage = open(URL, MainPage.class);
    }

    @After
    public void clear() {
        if (Objects.equals(WebDriverRunner.getWebDriver().getCurrentUrl(), LOGIN_URL)) {
            Login loginObject = createObjectLogin(email, password);
            Response responseLoginCourier = sendPostRequestAuthLogin(loginObject);
            LoginResponse loginResponse = deserialization(responseLoginCourier);
            String accessToken = loginResponse.getAccessToken();

            Response responseDeleteCourier = sendDeleteRequestAuthUser(accessToken);
            checkExpectedResult(responseDeleteCourier, SC_ACCEPTED, EXPECTED_RESULT_TRUE);
        }
    }

    @Test
    @DisplayName("Успешая регистрация")
    public void checkSuccessfulRegistration() {
        loginPage = mainPage.clickLogInToAccountButton();
        registerPage = loginPage.clickRegister();
        registerPage.setName(name);
        registerPage.setEmail(email);
        registerPage.setPassword(password);
        registerPage.clickRegisterButtonForLogin();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(LOGIN_URL, currentUrl);
    }

    @Test
    @DisplayName("Ошибка Минимальный пароль — шесть символов")
    public void checkErrorText() {
        loginPage = mainPage.clickLogInToAccountButton();
        registerPage = loginPage.clickRegister();
        registerPage.setPassword(shortPassword);
        registerPage.clickRegisterButton();
        registerPage.compareText(INVALID_PASSWORD);
    }

    @Step("Проверка соответствия ожидаемого результата")
    public void checkExpectedResult(Response response, int statusCode, boolean expectedResult) {
        response.then().assertThat().statusCode(statusCode).and().body("success", equalTo(expectedResult));
    }

    @Step("Создание объекта логин")
    public Login createObjectLogin(String email, String password) {
        return new Login(email, password);
    }

    @Step("Десериализация ответа на логин пользователя")
    public LoginResponse deserialization(Response responseLoginUser) {
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

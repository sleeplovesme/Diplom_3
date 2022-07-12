package tests;

import clients.LoginClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
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
import static org.apache.http.HttpStatus.SC_ACCEPTED;
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
            Login loginObject = LoginClient.createObjectLogin(email, password);
            Response responseLoginCourier = LoginClient.sendPostRequestAuthLogin(loginObject);
            LoginResponse loginResponse = LoginClient.deserialization(responseLoginCourier);
            String accessToken = loginResponse.getAccessToken();

            Response responseDeleteCourier = LoginClient.sendDeleteRequestAuthUser(accessToken);
            LoginClient.checkExpectedResult(responseDeleteCourier, SC_ACCEPTED, EXPECTED_RESULT_TRUE);
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
}

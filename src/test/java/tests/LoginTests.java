package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;

public class LoginTests {

    final String DEFAULT_EMAIL = "test-data@yandex.ru";
    final String DEFAULT_PASSWORD = "password";
    final String URL = "https://stellarburgers.nomoreparties.site/";
    final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";

    MainPage mainPage;
    LoginPage loginPage;
    ProfilePage profilePage;
    RegisterPage registerPage;
    ForgotPasswordPage forgotPasswordPage;

    @Before
    public void openMainPage() {
        Configuration.startMaximized = true;
        mainPage = open(URL, MainPage.class);
    }

    @After
    public void logOut() {
        profilePage = mainPage.clickPersonalAccountButton();
        profilePage.clickLogOut();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(LOGIN_URL, currentUrl);
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void checkLogInUsingLogInToAccountButton() {
        loginPage = mainPage.clickLogInToAccountButton();
        loginPage.setEmail(DEFAULT_EMAIL);
        loginPage.setPassword(DEFAULT_PASSWORD);
        mainPage = loginPage.clickLogin();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(URL, currentUrl);
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет")
    public void checkLogInUsingProfileButton() {
        loginPage = mainPage.clickProfileButton();
        loginPage.setEmail(DEFAULT_EMAIL);
        loginPage.setPassword(DEFAULT_PASSWORD);
        mainPage = loginPage.clickLogin();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(URL, currentUrl);
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void checkLogInUsingButtonInRegistrationForm() {
        loginPage = mainPage.clickLogInToAccountButton();
        registerPage = loginPage.clickRegister();
        loginPage = registerPage.clickLogin();
        loginPage.setEmail(DEFAULT_EMAIL);
        loginPage.setPassword(DEFAULT_PASSWORD);
        mainPage = loginPage.clickLogin();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(URL, currentUrl);
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void checkLogInUsingButtonInForgotPasswordForm() {
        loginPage = mainPage.clickLogInToAccountButton();
        forgotPasswordPage = loginPage.clickForgotPassword();
        loginPage = forgotPasswordPage.clickLogin();
        loginPage.setEmail(DEFAULT_EMAIL);
        loginPage.setPassword(DEFAULT_PASSWORD);
        mainPage = loginPage.clickLogin();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(URL, currentUrl);
    }
}

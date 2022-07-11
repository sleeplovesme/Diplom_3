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

public class OpenConstructorTests {

    final String DEFAULT_EMAIL = "test-data@yandex.ru";
    final String DEFAULT_PASSWORD = "password";
    final String URL = "https://stellarburgers.nomoreparties.site/";
    final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";

    MainPage mainPage;
    LoginPage loginPage;
    ProfilePage profilePage;

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
    @DisplayName("Переход по клику на «Конструктор»")
    public void checkOpenConstructorAfterClickToConstructor() {
        loginPage = mainPage.clickLogInToAccountButton();
        loginPage.setEmail(DEFAULT_EMAIL);
        loginPage.setPassword(DEFAULT_PASSWORD);
        mainPage = loginPage.clickLogin();
        profilePage = mainPage.clickPersonalAccountButton();
        mainPage = profilePage.clickConstructor();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(URL, currentUrl);
    }

    @Test
    @DisplayName("Переход по клику на логотип Stellar Burgers")
    public void checkOpenConstructorAfterClickToStellarBurgersLogo() {
        loginPage = mainPage.clickLogInToAccountButton();
        loginPage.setEmail(DEFAULT_EMAIL);
        loginPage.setPassword(DEFAULT_PASSWORD);
        mainPage = loginPage.clickLogin();
        profilePage = mainPage.clickPersonalAccountButton();
        mainPage = profilePage.clickStellarBurgersLogo();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(URL, currentUrl);
    }
}

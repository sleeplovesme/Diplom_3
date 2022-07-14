package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.ProfilePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertEquals;

public class OpenProfileTest {

    final String DEFAULT_EMAIL = "test-data@yandex.ru";
    final String DEFAULT_PASSWORD = "password";
    final String URL = "https://stellarburgers.nomoreparties.site/";
    final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";
    final String ACCOUNT_PROFILE_URL = "https://stellarburgers.nomoreparties.site/account/profile";

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
    @DisplayName("Переход в личный кабинет (профиль)")
    public void checkLogInUsingLogInToAccountButton() {
        loginPage = mainPage.clickLogInToAccountButton();
        loginPage.setEmail(DEFAULT_EMAIL);
        loginPage.setPassword(DEFAULT_PASSWORD);
        mainPage = loginPage.clickLogin();
        mainPage.clickPersonalAccountButton();
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(ACCOUNT_PROFILE_URL, currentUrl);
    }
}

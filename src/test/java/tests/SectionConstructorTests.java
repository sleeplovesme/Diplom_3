package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import pageobject.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class SectionConstructorTests {

    private final static String BUNS = "Булки";
    private final static String SAUCES = "Соусы";
    private final static String FILLINGS = "Начинки";

    MainPage mainPage;
    final String URL = "https://stellarburgers.nomoreparties.site/";

    @Before
    public void openMainPage() {
        Configuration.startMaximized = true;
        mainPage = open(URL, MainPage.class);
    }

    @Test
    @DisplayName("Переход к разделу булки")
    public void checkOpenBunsTab() {
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();
        mainPage.compareText(BUNS);
    }

    @Test
    @DisplayName("Переход к разделу соусы")
    public void checkOpenSaucesTab() {
        mainPage.clickSaucesTab();
        mainPage.compareText(SAUCES);
    }

    @Test
    @DisplayName("Переход к разделу начинки")
    public void checkOpenFillingsTab() {
        mainPage.clickFillingsTab();
        mainPage.compareText(FILLINGS);
    }
}

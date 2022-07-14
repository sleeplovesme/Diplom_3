package pageobject;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {

    // локатор кнопки Выход
    @FindBy(how = How.CLASS_NAME, using = "Account_button__14Yp3")
    private SelenideElement exitButton;

    // локатор кнопки Конструктор
    @FindBy(how = How.CLASS_NAME, using = "AppHeader_header__linkText__3q_va")
    private SelenideElement ConstructorLinkText;

    // локатор логотипа Stellar Burgers
    @FindBy(how = How.CLASS_NAME, using = "AppHeader_header__logo__2D0X2")
    private SelenideElement StellarBurgersLogo;

    @Step("Нажать Выйти")
    public LoginPage clickLogOut() {
        exitButton.click();
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.waitForLoadLoginPage();
        return loginPage;
    }

    @Step("Нажать Конструктор")
    public MainPage clickConstructor() {
        ConstructorLinkText.click();
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.waitForLoadMainPage();
        return mainPage;
    }

    @Step("Нажать на логотип Stellar Burgers")
    public MainPage clickStellarBurgersLogo() {
        StellarBurgersLogo.click();
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.waitForLoadMainPage();
        return mainPage;
    }

    @Step("Ожидание загрузки страницы")
    public void waitForLoadProfilePage() {
        $(byText("Выход")).shouldBe(visible);
    }
}

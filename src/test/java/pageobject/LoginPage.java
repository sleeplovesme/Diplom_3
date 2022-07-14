package pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    // локатор инпутов Email и Пароль
    @FindBy(how = How.CLASS_NAME, using = "input__textfield")
    private ElementsCollection inputs;

    // локатор кнопки Войти
    @FindBy(how = How.CLASS_NAME, using = "button_button__33qZ0")
    private SelenideElement loginButton;

    // локатор ссылок Зарегистрироваться и Восстановить пароль
    @FindBy(how = How.CLASS_NAME, using = "Auth_link__1fOlj")
    private ElementsCollection linkText;

    @Step("Ввести email")
    public void setEmail(String email) {
        inputs.get(0).setValue(email);
    }

    @Step("Ввести пароль")
    public void setPassword(String password) {
        inputs.get(1).setValue(password);
    }

    @Step("Нажать Войти")
    public MainPage clickLogin() {
        loginButton.click();
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.waitForLoadMainPage();
        return mainPage;
    }

    @Step("Нажать Зарегистрироваться")
    public RegisterPage clickRegister() {
        linkText.get(0).click();
        RegisterPage registerPage = Selenide.page(RegisterPage.class);
        registerPage.waitForLoadRegisterPage();
        return registerPage;
    }

    @Step("Нажать Восстановить пароль")
    public ForgotPasswordPage clickForgotPassword() {
        linkText.get(1).click();
        ForgotPasswordPage forgotPasswordPage = Selenide.page(ForgotPasswordPage.class);
        forgotPasswordPage.waitForLoadForgotPasswordPage();
        return forgotPasswordPage;
    }

    @Step("Ожидание загрузки страницы")
    public void waitForLoadLoginPage() {
        $(byText("Вход")).shouldBe(visible);
    }
}

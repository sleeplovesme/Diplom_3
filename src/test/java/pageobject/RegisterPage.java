package pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {

    // локатор полей Имя, Email, Пароль
    @FindBy(how = How.CLASS_NAME, using = "input__textfield")
    private ElementsCollection inputs;

    // локатор кнопки Зарегистрироваться
    @FindBy(how = How.CLASS_NAME, using = "button_button__33qZ0")
    private SelenideElement registerButton;

    // локатор поля Некорректный пароль
    @FindBy(how = How.CLASS_NAME, using = "input__error")
    private SelenideElement invalidPasswordInput;

    // локатор linkText Войти
    @FindBy(how = How.CLASS_NAME, using = "Auth_link__1fOlj")
    private SelenideElement loginText;

    @Step("Ввести Имя")
    public void setName(String inputName) {
        inputs.get(0).setValue(inputName);
    }

    @Step("Ввести Email")
    public void setEmail(String inputName) {
        inputs.get(1).setValue(inputName);
    }

    @Step("Ввести Пароль")
    public void setPassword(String inputName) {
        inputs.get(2).setValue(inputName);
    }

    @Step("Нажать Зарегистрироваться")
    public void clickRegisterButton() {
        registerButton.click();
    }

    @Step("Нажать Зарегистрироваться")
    public LoginPage clickRegisterButtonForLogin() {
        registerButton.click();
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.waitForLoadLoginPage();
        return loginPage;
    }

    @Step("Нажать Войти")
    public LoginPage clickLogin() {
        loginText.click();
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.waitForLoadLoginPage();
        return loginPage;
    }

    @Step("Проверка текста ошибки некорректного пароля")
    public void compareText(String expectedText) {
        invalidPasswordInput.shouldBe(visible).shouldHave(exactText(expectedText));
    }

    @Step("Ожидание загрузки страницы")
    public void waitForLoadRegisterPage() {
        $(byText("Регистрация")).shouldBe(visible);
    }
}

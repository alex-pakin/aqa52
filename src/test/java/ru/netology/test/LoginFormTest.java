package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.User;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class LoginFormTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginRegisteredActiveUser() {
        User user = DataGenerator.Registration.getRegisteredUser("active");
        $x("//*[@data-test-id='login']//input").val(user.getLogin());
        $x("//*[@data-test-id='password']//input").val(user.getPassword());
        $x("//*[@data-test-id='action-login']").click();
        $x("//*[@id='root']").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldGetErrorIfLoginNotValid() {
        User user = DataGenerator.Registration.getRegisteredUser("active");
        String invalidLogin = DataGenerator.getRandomLogin();
        $x("//*[@data-test-id='login']//input").val(invalidLogin);
        $x("//*[@data-test-id='password']//input").val(user.getPassword());
        $x("//*[@data-test-id='action-login']").click();
        $x("//*[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!"
                + " Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorIfPasswordNotValid() {
        User user = DataGenerator.Registration.getRegisteredUser("active");
        String invalidPassword = DataGenerator.getRandomPassword();
        $x("//*[@data-test-id='login']//input").val(user.getLogin());
        $x("//*[@data-test-id='password']//input").val(invalidPassword);
        $x("//*[@data-test-id='action-login']").click();
        $x("//*[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!"
                + " Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorIfUserIsBlocked() {
        User user = DataGenerator.Registration.getRegisteredUser("blocked");
        $x("//*[@data-test-id='login']//input").val(user.getLogin());
        $x("//*[@data-test-id='password']//input").val(user.getPassword());
        $x("//*[@data-test-id='action-login']").click();
        $x("//*[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!"
                + " Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorIfUserNotRegistered() {
        User user = DataGenerator.Registration.getUser("active");
        $x("//*[@data-test-id='login']//input").val(user.getLogin());
        $x("//*[@data-test-id='password']//input").val(user.getPassword());
        $x("//*[@data-test-id='action-login']").click();
        $x("//*[@data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!"
                + " Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

}

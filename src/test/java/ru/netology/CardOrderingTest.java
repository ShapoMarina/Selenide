package ru.netology;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class CardOrderingTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    String generateDate(int monthesToAdd, String pattern) {
        return LocalDate.now().plusDays(monthesToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldValidTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Пенза");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String SelectedDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(SelectedDate);
        $("[data-test-id='name'] input").setValue("Бычкова Анна");
        $("[data-test-id='phone'] input").setValue("+79371234567");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + SelectedDate));


    }
}

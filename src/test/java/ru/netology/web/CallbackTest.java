package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackTest {
    private WebDriver driver;
    private WebElement actualTextElement;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void SuccessfulForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79220435567");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.className("paragraph")).getText().trim();

        assertEquals(expected, actual);
        assertTrue(actualTextElement.isDisplayed());
    }

    //невалидное имя
    @Test
    void invalidName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Petrov Petr");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79220435567");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
        assertTrue(actualTextElement.isDisplayed());
    }

    //пустое имя
    @Test
    void NoName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79220435567");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();

        assertEquals(expected, actual);
        assertTrue(actualTextElement.isDisplayed());
    }

    // пустой чекбокс
    @Test
    void NoClickCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79220435567");
        driver.findElement(By.tagName("button")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();

        assertEquals(expected, actual);
        assertTrue(actualTextElement.isDisplayed());
    }

    //невалидный телефон
    @Test
    void invalidPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+792204355");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();

        assertEquals(expected, actual);
        assertTrue(actualTextElement.isDisplayed());
    }

    //пустой телефон
    @Test
    void NoPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Петров Петр");
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.tagName("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();

        assertEquals(expected, actual);
        assertTrue(actualTextElement.isDisplayed());
    }
}
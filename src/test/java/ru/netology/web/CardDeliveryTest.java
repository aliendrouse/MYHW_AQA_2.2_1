package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Nested
    class cardDeliveryTest {
        public String setLocalDate(int days) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy",
                    new Locale("ru")));
        }

        @Test
        void application() {
            String date = setLocalDate(3);
            $("[data-test-id=city] input").setValue("Волгоград");
            //$(byText("Выберите дату встречи с представителем банка")).click();
            $("[data-test-id=date] input").doubleClick().sendKeys(date);
            $("[data-test-id=name] input").setValue("Иванов Иван");
            $("[data-test-id=phone] input").setValue("+79998887766");
            //$x("//*[text()='Я соглашаюсь с условиями обработки и использования моих персональных данных']").click();
            $("[data-test-id=agreement]").click();
            $$("button").find(exactText("Забронировать")).click();
            $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
            $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                    Duration.ofSeconds(15)).shouldBe(visible);
        }
    }
}
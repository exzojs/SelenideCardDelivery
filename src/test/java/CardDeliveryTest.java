import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    @BeforeEach
    void openSite(){
        open("http://localhost:9999/");
    }

    @Test
    void shouldCardDelivery(){
        String date = generateDate(3);
        $("[data-test-id= city] input").setValue("Москва");
        $("[data-test-id= date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id= date] input").setValue(date);
        $("[data-test-id= name] input").setValue("Евгений Иванов-Петров");
        $("[data-test-id= phone] input").setValue("+79999999999");
        $("[data-test-id= agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id= notification]").shouldHave(exactText("Успешно! " + "Встреча успешно забронирована на " + date), Duration.ofSeconds(15)).shouldBe(exist);
    }
}

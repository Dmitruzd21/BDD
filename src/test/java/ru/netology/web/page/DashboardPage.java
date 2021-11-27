package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private SelenideElement replenishmentButtonBefore1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] .button__text");
    private SelenideElement replenishmentButtonBefore2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] .button__text");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    // метод получения начального баланса карт
    public int getBalanceOfCard(int cardIndex) {
        var text = cards.get(cardIndex - 1).text();
        return exctractBalance(text);
    }

    public int exctractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    // метод нажатия кнопки для переключения на страницу оплаты
    public ReplenishmentPage clickReplenishmentButton(String from1To2OrFrom2to1) {
        if (from1To2OrFrom2to1 == "from1to2") {
            replenishmentButtonBefore2.click();
        } else {
            replenishmentButtonBefore1.click();
        }
        return new ReplenishmentPage();
    }
}
package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р. ";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getInitialBalanceOfCard(int cardIndex) {
        if (cardIndex == 1) {
            var text = cards.first().text();
        }
        if (cardIndex == 2) {
            var text = cards.last().text();
        }
        return exctractBalance(text);
    }

    public int exctractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }




}
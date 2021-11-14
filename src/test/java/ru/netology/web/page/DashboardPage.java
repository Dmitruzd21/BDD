package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement firstCard = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    private SelenideElement secondCard = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");
    private ElementsCollection cards = $$ (".list__item");
    private SelenideElement replenishmentButtonBefore1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] .button__text");
    private SelenideElement replenishmentButtonBefore2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] .button__text");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    // data - класс для хранеия и извлечения информации о начальном и конечном балансе карт
    public class CardsBalance {
        int card1Balance;
        int card2Balance;

        public void setCard1Balance(int card1Balance) {
            this.card1Balance = card1Balance;
        }

        public void setCard2Balance(int card2Balance) {
            this.card2Balance = card2Balance;
        }

        public int getCard1Balance() {
            return card1Balance;
        }

        public int getCard2Balance() {
            return card2Balance;
        }

    }

    // метод получения начального баланса карт
    public int getInitialBalanceOfCard(int cardIndex) {
        var initialCardsBalance = new CardsBalance();
        var text = cards.get(cardIndex-1).text();
        return exctractBalance(text);
    }

    public int exctractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }


    // метод для вычисления окончательного баланса карт
    public CardsBalance finalBalance(String from1To2OrFrom2to1, int initialBalanceCard1, int initialBalanceCard2, int transferAmount) {
        var сardsBalance = new CardsBalance();
        if (from1To2OrFrom2to1 == "from1To2") {
            int finalBalanceOfTheFirstCard = initialBalanceCard1 - transferAmount;
            int finalBalanceOfTheSecondCard = initialBalanceCard2 + transferAmount;
            сardsBalance.setCard1Balance(finalBalanceOfTheFirstCard);
            сardsBalance.setCard2Balance(finalBalanceOfTheSecondCard);
        }
        if (from1To2OrFrom2to1 == "from2To1") {
            int finalBalanceOfTheFirstCard = initialBalanceCard1 + transferAmount;
            int finalBalanceOfTheSecondCard = initialBalanceCard2 - transferAmount;
            сardsBalance.setCard1Balance(finalBalanceOfTheFirstCard);
            сardsBalance.setCard2Balance(finalBalanceOfTheSecondCard);
        }
        return сardsBalance;
    }

    // метод проверки окончательного баланса
    public int checkFinalBalance(String from1To2OrFrom2to1, int initialBalanceCard1, int initialBalanceCard2, int transferAmount) {
        int finalBalanceOfCard1 = finalBalance(from1To2OrFrom2to1, initialBalanceCard1, initialBalanceCard2, transferAmount).getCard1Balance();
        int finalBalanceOfCard2 = finalBalance(from1To2OrFrom2to1, initialBalanceCard1, initialBalanceCard2, transferAmount).getCard2Balance();
        // проверка осуществления перевода
        firstCard.shouldHave(text(String.valueOf(finalBalanceOfCard1)));
        secondCard.shouldHave(text(String.valueOf(finalBalanceOfCard2)));
        // возвращение баланса в тест для последующего ассерта в тесте с негативным сценарием
        int cardBalance = 0;
        if (from1To2OrFrom2to1 == "from1To2") {
            cardBalance = finalBalanceOfCard1;
        }
        if (from1To2OrFrom2to1 == "from2To1") {
            cardBalance = finalBalanceOfCard2;
        }
        return cardBalance;
    }

    // метод нажатия кнопки для переключения на страницу оплаты
    public ReplenishmentPage clickReplenishmentButton (String from1To2OrFrom2to1) {
        if (from1To2OrFrom2to1=="from1to2") {
            replenishmentButtonBefore2.click();
        } else {
            replenishmentButtonBefore1.click();
        }
        return new ReplenishmentPage();
    }

}
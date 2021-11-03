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
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement firstCard = $$("#root div ul li:nth-child(1) div").first();
    private SelenideElement secondCard = $$("#root div ul li:nth-child(2) div").last();

    // сумма перевода
    //private int transferAmount = ReplenishmentPage.getAmountValueInt();

    public DashboardPage() {
        heading.shouldBe(visible);
    }


    // data - класс для хранеия и извлечения информации о начальном балансе карт
    public class InitialCardsBalance {
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
        var initialCardsBalance = new InitialCardsBalance();
        var text = "";
        int initialCardBalance = 0;
        if (cardIndex == 1) {
            text = cards.first().text();
            initialCardsBalance.setCard1Balance(exctractBalance(text));
            initialCardBalance = initialCardsBalance.getCard1Balance();
        }
        if (cardIndex == 2) {
            text = cards.last().text();
            initialCardsBalance.setCard2Balance(exctractBalance(text));
            initialCardBalance = initialCardsBalance.getCard2Balance();
        }
        return initialCardBalance;
    }

    public int exctractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    // data - класс для хранеия и извлечения информации о конечном балансе карт
    public class FinalCardsBalance {
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

    // метод для вычисления окончательного баланса карт
    public FinalCardsBalance finalBalance(String from1To2OrFrom2to1, int initialBalanceCard1, int initialBalanceCard2, int transferAmount) {
        var сardsBalance = new FinalCardsBalance();
        var initialBalance = new InitialCardsBalance();
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
        int cardBalance = 0;
        if (from1To2OrFrom2to1 == "from1To2") {
            cardBalance = finalBalanceOfCard1;
        }
        if (from1To2OrFrom2to1 == "from2To1"){
            cardBalance = finalBalanceOfCard2;
        }
        return cardBalance;
    }

}
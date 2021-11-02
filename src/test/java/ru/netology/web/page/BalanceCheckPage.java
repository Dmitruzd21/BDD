package ru.netology.web.page;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.sun.xml.bind.v2.TODO;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;

public class BalanceCheckPage {
    private SelenideElement dashboard = $("[data-test-id=dashboard]");
    private SelenideElement firstCard = $$("#root div ul li:nth-child(1) div").first();
    private SelenideElement secondCard = $$("#root div ul li:nth-child(2) div").last();


    // сумма перевода
    private int transferAmount = ReplenishmentPage.getAmountValueInt();

    // 1я карта (начальный баланс)
    private int initialBalanceOfTheFirstCard = DataHelper.getCard1Info().getBalance();

    // 2я карта (начальный баланс)
    private int initialBalanceOfTheSecondCard = DataHelper.getCard2Info().getBalance();

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

    // класс для вычисления окончательного баланса карт
    public CardsBalance finalBalance(String from1To2OrFrom2to1) {
        var сardsBalance = new CardsBalance();
        if (from1To2OrFrom2to1 == "from1To2") {
            int finalBalanceOfTheFirstCard = initialBalanceOfTheFirstCard - transferAmount;
            int finalBalanceOfTheSecondCard = initialBalanceOfTheSecondCard + transferAmount;
            сardsBalance.setCard1Balance(finalBalanceOfTheFirstCard);
            сardsBalance.setCard2Balance(finalBalanceOfTheSecondCard);
        }
        if (from1To2OrFrom2to1 == "from2To1") {
            int finalBalanceOfTheFirstCard = initialBalanceOfTheFirstCard + transferAmount;
            int finalBalanceOfTheSecondCard = initialBalanceOfTheSecondCard - transferAmount;
            сardsBalance.setCard1Balance(finalBalanceOfTheFirstCard);
            сardsBalance.setCard2Balance(finalBalanceOfTheSecondCard);
        }
        return сardsBalance;
    }

    // класс проверки окончательного баланса
    public void checkFinalBalance(String from1To2OrFrom2to1) {
        int finalBalanceOfCard1 = finalBalance(from1To2OrFrom2to1).getCard1Balance();
        int finalBalanceOfCard2 = finalBalance(from1To2OrFrom2to1).getCard2Balance();
        firstCard.shouldHave(text(String.valueOf(finalBalanceOfCard1)));
        secondCard.shouldHave(text(String.valueOf(finalBalanceOfCard2)));
    }

}

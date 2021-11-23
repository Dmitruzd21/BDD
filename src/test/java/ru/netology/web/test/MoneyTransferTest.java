package ru.netology.web.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    private SelenideElement firstCard = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    private SelenideElement secondCard = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");

    @BeforeEach
        // метод сравнивает баланс карт, если он не равен, то разницу между картами отправляет на карту, где баланс меньше
    void shouldRestoreCardsBalanceIfItIsNeeded() {
        String secondCardNumber = DataHelper.getCard2Info().getNumber();
        String firstCardNumber = DataHelper.getCard1Info().getNumber();
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        if (initialBalanceCard1 == initialBalanceCard2) {
            String status = "The balances of the two cards are equal";
        } else {
            //////////
            if (initialBalanceCard1 < initialBalanceCard2) {
                String differenceBetweenCards = String.valueOf(initialBalanceCard2 - 10_000);
                var replenishmentPage = dashboardPage.clickReplenishmentButton("from2to1");
                replenishmentPage.replenishment(secondCardNumber, differenceBetweenCards);
            } else {
                String differenceBetweenCards = String.valueOf(initialBalanceCard1 - 10_000);
                var replenishmentPage = dashboardPage.clickReplenishmentButton("from1to2");
                replenishmentPage.replenishment(firstCardNumber, differenceBetweenCards);
            }
            //////////
        }
    }

    @Test
    @DisplayName("TransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueLessInitialBalance")
    void shouldTransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueLessInitialBalance() {
        String amountValue = "1000";
        String secondCardNumber = DataHelper.getCard2Info().getNumber();
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = dashboardPage.clickReplenishmentButton("from2to1");
        replenishmentPage.replenishment(secondCardNumber, amountValue);
        int finalBalanceOfCard1 = dashboardPage.finalBalance("from2To1", 1, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        int finalBalanceOfCard2 = dashboardPage.finalBalance("from2To1", 2, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        // проверка осуществления перевода
        firstCard.shouldHave(text(String.valueOf(finalBalanceOfCard1)));
        secondCard.shouldHave(text(String.valueOf(finalBalanceOfCard2)));
    }

    @Test
    @DisplayName("TransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueLessInitialBalance")
    void shouldTransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueLessInitialBalance() {
        String amountValue = "1000";
        String firstCardNumber = DataHelper.getCard1Info().getNumber();
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = dashboardPage.clickReplenishmentButton("from1to2");
        replenishmentPage.replenishment(firstCardNumber, amountValue);
        int finalBalanceOfCard1 = dashboardPage.finalBalance("from1To2", 1, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        int finalBalanceOfCard2 = dashboardPage.finalBalance("from1To2", 2, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        // проверка осуществления перевода
        firstCard.shouldHave(text(String.valueOf(finalBalanceOfCard1)));
        secondCard.shouldHave(text(String.valueOf(finalBalanceOfCard2)));
    }

    @Test
    @DisplayName("shouldNotTransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueMoreInitialBalance")
    void shouldNotTransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueMoreInitialBalance() {
        String amountValue = "35000";
        String secondCardNumber = DataHelper.getCard2Info().getNumber();
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = dashboardPage.clickReplenishmentButton("from2to1");
        replenishmentPage.replenishment(secondCardNumber, amountValue);
        int finalBalanceOfCard1 = dashboardPage.finalBalance("from2To1", 1, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        int finalBalanceOfCard2 = dashboardPage.finalBalance("from2To1", 2, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        // проверка осуществления перевода
        firstCard.shouldHave(text(String.valueOf(finalBalanceOfCard1)));
        secondCard.shouldHave(text(String.valueOf(finalBalanceOfCard2)));
        // проверка того, что баланс второй карты больше нуля
        assertTrue(finalBalanceOfCard2 > 0);
    }

    @Test
    @DisplayName("shouldNotTransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueMoreInitialBalance")
    void shouldNotTransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueMoreInitialBalance() {
        String amountValue = "35000";
        String firstCardNumber = DataHelper.getCard1Info().getNumber();
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = dashboardPage.clickReplenishmentButton("from1to2");
        replenishmentPage.replenishment(firstCardNumber, amountValue);
        int finalBalanceOfCard1 = dashboardPage.finalBalance("from1To2", 1, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        int finalBalanceOfCard2 = dashboardPage.finalBalance("from1To2", 2, initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        // проверка осуществления перевода
        firstCard.shouldHave(text(String.valueOf(finalBalanceOfCard1)));
        secondCard.shouldHave(text(String.valueOf(finalBalanceOfCard2)));
        // проверка того, что баланс первой карты больше нуля
        assertTrue(finalBalanceOfCard1 > 0);
    }

}
package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
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
        int initialBalanceCard1 = dashboardPage.getBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getBalanceOfCard(2);
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
        var dashboardPageBefore = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPageBefore.getBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPageBefore.getBalanceOfCard(2);
        var replenishmentPage = dashboardPageBefore.clickReplenishmentButton("from2to1");
        var dashboardPageAfter = replenishmentPage.replenishment(secondCardNumber, amountValue);
        // проверка того, что ожидаемый конечный баланс равен фактическому
        int expectedFinalBalanceOfCard1 = initialBalanceCard1 + Integer.parseInt(amountValue);
        int expectedFinalBalanceOfCard2 = initialBalanceCard2 - Integer.parseInt(amountValue);
        int actualFinalBalanceOfCard1 = dashboardPageAfter.getBalanceOfCard(1);
        int actualFinalBalanceOfCard2 = dashboardPageAfter.getBalanceOfCard(2);
        assertEquals(expectedFinalBalanceOfCard1, actualFinalBalanceOfCard1);
        assertEquals(expectedFinalBalanceOfCard2, actualFinalBalanceOfCard2);
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
        var dashboardPageBefore = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPageBefore.getBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPageBefore.getBalanceOfCard(2);
        var replenishmentPage = dashboardPageBefore.clickReplenishmentButton("from1to2");
        var dashboardPageAfter = replenishmentPage.replenishment(firstCardNumber, amountValue);
        // проверка того, что ожидаемый конечный баланс равен фактическому
        int expectedFinalBalanceOfCard1 = initialBalanceCard1 - Integer.parseInt(amountValue);
        int expectedFinalBalanceOfCard2 = initialBalanceCard2 + Integer.parseInt(amountValue);
        int actualFinalBalanceOfCard1 = dashboardPageAfter.getBalanceOfCard(1);
        int actualFinalBalanceOfCard2 = dashboardPageAfter.getBalanceOfCard(2);
        assertEquals(expectedFinalBalanceOfCard1, actualFinalBalanceOfCard1);
        assertEquals(expectedFinalBalanceOfCard2, actualFinalBalanceOfCard2);
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
        var dashboardPageBefore = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPageBefore.getBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPageBefore.getBalanceOfCard(2);
        var replenishmentPage = dashboardPageBefore.clickReplenishmentButton("from2to1");
        var dashboardPageAfter = replenishmentPage.replenishment(secondCardNumber, amountValue);
        // проверка того, что фактический баланс второй карты больше ожидаемого значения (нуля)
        int actualFinalBalanceOfCard2 = dashboardPageAfter.getBalanceOfCard(2);
        assertTrue(actualFinalBalanceOfCard2 > 0);
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
        var dashboardPageBefore = verificationPage.validVerify(verificationCode);
        int initialBalanceCard1 = dashboardPageBefore.getBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPageBefore.getBalanceOfCard(2);
        var replenishmentPage = dashboardPageBefore.clickReplenishmentButton("from1to2");
        var dashBoardPageAfter = replenishmentPage.replenishment(firstCardNumber, amountValue);
        // проверка того, что фактический баланс первой карты больше ожидаемого значения (нуля)
        int actualFinalBalanceOfCard1 = dashBoardPageAfter.getBalanceOfCard(1);
        assertTrue(actualFinalBalanceOfCard1 > 0);
    }

}
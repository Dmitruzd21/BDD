package ru.netology.web.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    @Test
    @DisplayName("TransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueLessInitialBalance")
    void shouldTransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueLessInitialBalance() {
        String amountValue = "1000";
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.replenishment("from2To1", amountValue);
        dashboardPage.checkFinalBalance("from2To1", initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
    }

    @Test
    @DisplayName("TransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueLessInitialBalance")
    void shouldTransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueLessInitialBalance() {
        String amountValue = "1000";
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.replenishment("from1To2", amountValue);
        dashboardPage.checkFinalBalance("from1To2", initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
    }

    @Test
    @DisplayName("GetErrorWhenTransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueMoreInitialBalance")
    void shouldGetErrorWhenTransferMoneyBetweenOwnCardsFrom2ndTo1stIfAmountValueMoreInitialBalance() {
        String amountValue = "35000";
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.replenishment("from2To1", amountValue);
        int finalBalanceActual = dashboardPage.checkFinalBalance("from2To1", initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        assertTrue(finalBalanceActual > 0);
    }

    @Test
    @DisplayName("GetErrorWhenTransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueMoreInitialBalance")
    void shouldGetErrorWhenTransferMoneyBetweenOwnCardsFrom1stTo2ndIfAmountValueMoreInitialBalance() {
        String amountValue = "335000";
        open("http://localhost:9999");
        var loginPage = new LoginPageV2();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        int initialBalanceCard1 = dashboardPage.getInitialBalanceOfCard(1);
        int initialBalanceCard2 = dashboardPage.getInitialBalanceOfCard(2);
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.replenishment("from1To2", amountValue);
        int finalBalanceActual = dashboardPage.checkFinalBalance("from1To2", initialBalanceCard1, initialBalanceCard2, Integer.parseInt(amountValue));
        assertTrue(finalBalanceActual > 0);
    }

}
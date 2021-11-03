package ru.netology.web.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    @Test
    @DisplayName("Валидный перевод с 2й карты на 1ю")
    void shouldTransferMoneyBetweenOwnCardsFrom2ndTo1st() {
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
        replenishmentPage.replenishment("from2To1");
        dashboardPage.checkFinalBalance("from2To1", initialBalanceCard1,initialBalanceCard2);
    }

    @Test
    @DisplayName("Валидный перевод с 1й карты на 2ю")
    void shouldTransferMoneyBetweenOwnCardsFrom1stTo2nd() {
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
        replenishmentPage.replenishment("from1To2");
        dashboardPage.checkFinalBalance("from1To2",initialBalanceCard1,initialBalanceCard2);
    }


}
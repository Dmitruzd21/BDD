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
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.replenishment("from2To1");
        var balanceChekPage = new BalanceCheckPage();
        balanceChekPage.checkFinalBalance("from2To2");
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
        var replenishmentPage = new ReplenishmentPage();
        replenishmentPage.replenishment("from1To2");
        var balanceChekPage = new BalanceCheckPage();
        balanceChekPage.checkFinalBalance("from1To2");
    }


}
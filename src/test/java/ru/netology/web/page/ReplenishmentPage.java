package ru.netology.web.page;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;


public class ReplenishmentPage {

    private SelenideElement amount = $("[data-test-id=amount] .input__control");
    private SelenideElement from = $("[data-test-id=from] .input__control");
    private SelenideElement replenishmentButtonAfter = $("[data-test-id=action-transfer]");


    public void setAmountValue(String amountValue) {
    }

    public DashboardPage replenishment(String cardNumber, String amountValue) {
        amount.setValue(amountValue);
        from.setValue(cardNumber);
        replenishmentButtonAfter.click();
        return new DashboardPage();
    }

}

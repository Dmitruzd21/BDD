package ru.netology.web.page;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

public class ReplenishmentPage {

    private SelenideElement replenishmentButtonBefore1 = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"] .button__text");
    private SelenideElement replenishmentButtonBefore2 = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"] .button__text");
    private SelenideElement amount = $("[data-test-id=amount] .input__control");
    private SelenideElement from = $("[data-test-id=from] .input__control");
    private SelenideElement replenishmentButtonAfter = $("[data-test-id=action-transfer]");


    public void setAmountValue(String amountValue) {
    }

    private String firstCardNumber = DataHelper.getCard1Info().getNumber();
    private String secondCardNumber = DataHelper.getCard2Info().getNumber();


    public DashboardPage replenishment(String from1To2OrFrom2to1, String amountValue) {
        if (from1To2OrFrom2to1 == "from1To2") {
            replenishmentButtonBefore2.click();
            amount.setValue(amountValue);
            from.setValue(firstCardNumber);
            replenishmentButtonAfter.click();
        }
        if (from1To2OrFrom2to1 == "from2To1") {
            replenishmentButtonBefore1.click();
            amount.setValue(amountValue);
            from.setValue(secondCardNumber);
            replenishmentButtonAfter.click();
        }
        return new DashboardPage();
    }

}

package ru.netology.web.page;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

public class ReplenishmentPage {

    private SelenideElement replenishmentButtonBefore1 = $$("[data-test-id=action-deposit]").get(0);
    private SelenideElement replenishmentButtonBefore2 = $$("[data-test-id=action-deposit]").get(1);
    private SelenideElement amount = $("[data-test-id=amount] .input__control");
    private SelenideElement from = $("[data-test-id=from] .input__control");
    private SelenideElement replenishmentButtonAfter = $("[data-test-id=action-transfer]");
    private static String amountValue = "1000";

    public static Integer getAmountValueInt() {
        return Integer.valueOf(amountValue);
    }

    private String firstCardNumber = DataHelper.getCard1Info().getNumber();
    private String secondCardNumber = DataHelper.getCard2Info().getNumber();



    public DashboardPage replenishment(String from1To2OrFrom2to1) {
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

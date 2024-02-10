package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class LandingPage {
    private static final SelenideElement pageHeader =
            $$("h2").find(exactText("Путешествие дня"));
    private static final SelenideElement buyDirectButton =
            $$("button").find(exactText("Купить"));
    private static final SelenideElement buyInCreditButton =
            $$("button").find(exactText("Купить в кредит"));

    public LandingPage() {
        pageHeader.shouldBe(visible);
        buyDirectButton.shouldBe(visible);
        buyInCreditButton.shouldBe(visible);
    }

    public BuyDirectPage chooseDirectPurchaseAction() {
        buyDirectButton.click();
        return new BuyDirectPage();
    }

    public BuyInCreditPage chooseCreditAction() {
        buyInCreditButton.click();
        return new BuyInCreditPage();
    }
}

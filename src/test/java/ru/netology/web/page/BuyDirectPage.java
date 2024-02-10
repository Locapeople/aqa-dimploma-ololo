package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class BuyDirectPage extends PurchasePageBase {

    private static final SelenideElement pageHeader = $$("h3").find(text("Кредит по данным карты"));

    public BuyDirectPage() {
        super();
        pageHeader.shouldBe(visible);
    }
}

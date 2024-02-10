package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.PaymentCard;
//import data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

// Базовый класс для страницы обоих вариантов оплаты
public class PurchasePageBase {

    private static final SelenideElement field_cardNumber =
            $(byText("Номер карты")).parent().$(".input__control");
    private static final SelenideElement field_dueMonth =
            $(byText("Месяц")).parent().$(".input__control");
    private static final SelenideElement field_dueYear =
            $(byText("Год")).parent().$(".input__control");
    private static final SelenideElement field_holder =
            $(byText("Владелец")).parent().$(".input__control");
    private static final SelenideElement field_CVCCVV =
            $(byText("CVC/CVV")).parent().$(".input__control");
    private static final SelenideElement button_continue =
            $$("button").find(exactText("Продолжить"));
    private static final SelenideElement notification_OKStatus =
            $(".notification_status_ok");
    private static final SelenideElement notification_ErrorStatus =
            $(".notification_status_error");
    private static final SelenideElement text_invalidFormat = $(".input__sub");

    public PurchasePageBase() {
        field_cardNumber.shouldBe(visible);
        field_dueMonth.shouldBe(visible);
        field_dueYear.shouldBe(visible);
        field_holder.shouldBe(visible);
        field_CVCCVV.shouldBe(visible);
        button_continue.shouldBe(visible);
        notification_OKStatus.shouldBe(hidden);
        notification_ErrorStatus.shouldBe(hidden);
        text_invalidFormat.shouldBe(hidden);
    }

    // TODO: исправить имена и логику
    public void enterCardData(PaymentCard card) {
        field_cardNumber.setValue(card.getCardNumber());
        field_dueMonth.setValue(card.getDueMonth());
        field_dueYear.setValue(card.getDueYear());
        field_holder.setValue(card.getHolder());
        field_CVCCVV.setValue(card.getCVCCVV());
        button_continue.click();
    }

    public void isOKStatus() {
        notification_OKStatus.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void isErrorStatus() {
        notification_ErrorStatus.shouldBe(visible, Duration.ofSeconds(15));
    }

    public boolean isInputInvalid() {
        return text_invalidFormat.isDisplayed();
    }
}

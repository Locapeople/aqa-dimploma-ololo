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

    private static final SelenideElement FIELD_CARD_NUMBER =
            $(byText("Номер карты")).parent().$(".input__control");
    private static final SelenideElement FIELD_DUE_MONTH =
            $(byText("Месяц")).parent().$(".input__control");
    private static final SelenideElement FIELD_DUE_YEAR =
            $(byText("Год")).parent().$(".input__control");
    private static final SelenideElement FIELD_HOLDER =
            $(byText("Владелец")).parent().$(".input__control");
    private static final SelenideElement FIELD_CVCCVV =
            $(byText("CVC/CVV")).parent().$(".input__control");
    private static final SelenideElement BUTTON_CONTINUE =
            $$("button").find(exactText("Продолжить"));
    private static final SelenideElement NOTIFICATION_OK_STATUS =
            $(".notification_status_ok");
    private static final SelenideElement NOTIFICATION_ERROR_STATUS =
            $(".notification_status_error");
    private static final SelenideElement TEXT_INVALID_FORMAT = $(".input__sub");

    public PurchasePageBase() {
        FIELD_CARD_NUMBER.shouldBe(visible);
        FIELD_DUE_MONTH.shouldBe(visible);
        FIELD_DUE_YEAR.shouldBe(visible);
        FIELD_HOLDER.shouldBe(visible);
        FIELD_CVCCVV.shouldBe(visible);
        BUTTON_CONTINUE.shouldBe(visible);
        NOTIFICATION_OK_STATUS.shouldBe(hidden);
        NOTIFICATION_ERROR_STATUS.shouldBe(hidden);
        TEXT_INVALID_FORMAT.shouldBe(hidden);
    }

    // TODO: исправить имена и логику
    public void enterCardData(PaymentCard card) {
        FIELD_CARD_NUMBER.setValue(card.getCardNumber());
        FIELD_DUE_MONTH.setValue(card.getDueMonth());
        FIELD_DUE_YEAR.setValue(card.getDueYear());
        FIELD_HOLDER.setValue(card.getHolder());
        FIELD_CVCCVV.setValue(card.getCVCCVV());
        BUTTON_CONTINUE.click();
    }

    public void isOKStatus() {
        NOTIFICATION_OK_STATUS.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void isErrorStatus() {
        NOTIFICATION_ERROR_STATUS.shouldBe(visible, Duration.ofSeconds(15));
    }

    public boolean isInputInvalid() {
        return TEXT_INVALID_FORMAT.isDisplayed();
    }
}

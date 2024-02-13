package ru.netology.web.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import ru.netology.web.data.DataHelper;
import ru.netology.web.data.PaymentCard;
import ru.netology.web.page.LandingPage;
import ru.netology.web.page.BuyDirectPage;
import ru.netology.web.page.BuyInCreditPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripPurchaseDirectTest {
    public static final String SUT_URL = System.getProperty("sut.url");

    @BeforeEach
    public void openPage() {
        open(SUT_URL);
        DataHelper.DBHelper.clear();
    }

    // Тест №1
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard1.csv", numLinesToSkip = 1)
    @DisplayName("(1) Покупка билетов: корректные данные по карте 1 - ожидание сообщения об успехе")
    void shouldSucceedForValidCard1Input_DirectPurchase(String number, String dueMonthOffset, String dueYearOffset, String holder, String CVCCVV) {
        PaymentCard card1WithValidInfo = new PaymentCard(number,
                DataHelper.DateHelper.getDueMonthWithOffset(dueMonthOffset),
                DataHelper.DateHelper.getDueYearWithOffset(dueYearOffset),
                holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(card1WithValidInfo);
        buyPage.isOKStatus();
        assertEquals("APPROVED", DataHelper.DBHelper.getDirectPurchaseStatus());
    }

    // Тест №3
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard2.csv", numLinesToSkip = 1)
    @DisplayName("(3) Покупка билетов: корректные данные по карте 2 - ожидание сообщения об отказе")
    void shouldSucceedForValidCard2Input_DirectPurchase(String number, String dueMonthOffset, String dueYearOffset, String holder, String CVCCVV) {
        PaymentCard card2WithValidInfo = new PaymentCard(number,
                DataHelper.DateHelper.getDueMonthWithOffset(dueMonthOffset),
                DataHelper.DateHelper.getDueYearWithOffset(dueYearOffset),
                holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(card2WithValidInfo);
        buyPage.isErrorStatus();
        assertEquals("DECLINED", DataHelper.DBHelper.getCreditStatus());
    }

    // Тесты №5-9
    @ParameterizedTest
    @CsvFileSource(resources = "/emptyInputForEachField.csv", numLinesToSkip = 1)
    @DisplayName("(5-9) Покупка билетов: Отсутствие ввода в каждое поле по очереди - ожидание ошибки под полем")
    void shouldShowErrorIfAnyFieldValueIsEmpty_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) {
        PaymentCard cardWithOneEmptyField = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(cardWithOneEmptyField);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }

    // Тест №15
    @CsvFileSource(resources = "/unknownCardNumberInput.csv", numLinesToSkip = 1)
    @DisplayName("(15) Покупка билетов: Ввод неизвестного для SUT номера карты - ожидание сообщения об отказе")
    void shouldShowErrorIfUnknownCardNumber_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) {
        PaymentCard cardWithOneEmptyField = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(cardWithOneEmptyField);
        buyPage.isErrorStatus();
        assertEquals(0, DataHelper.DBHelper.count());
    }

    // Тесты №16-24
    @ParameterizedTest
    @CsvFileSource(resources = "/invalidInputForEachField.csv", numLinesToSkip = 1)
    @DisplayName("(15-24) Покупка билетов: Ввод некорректных данных в каждое поле по очереди - ожидание ошибки под полем")
    void shouldShowErrorIfAnyFieldValueIsInvalid_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) {
        PaymentCard cardWithOneInvalidFieldValue = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(cardWithOneInvalidFieldValue);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }
}

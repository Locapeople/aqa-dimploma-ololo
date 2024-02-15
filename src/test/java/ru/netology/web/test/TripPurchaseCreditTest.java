package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.PaymentCard;
import ru.netology.web.page.BuyInCreditPage;
import ru.netology.web.page.LandingPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripPurchaseCreditTest {
    public static final String SUT_URL = System.getProperty("sut.url");

    @BeforeEach
    public void openPageAndClearTables() {
        open(SUT_URL);
        DataHelper.DBHelper.clear();
    }

    // Тест №2
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard1.csv", numLinesToSkip = 1)
    @DisplayName("(2) Покупка билетов в кредит: корректные данные по карте 1 - ожидание сообщения об успехе")
    void shouldSucceedForValidCard1Input_Credit(String number, String dueMonthOffset, String dueYearOffset, String holder, String CVCCVV) {
        PaymentCard card1WithValidInfo = new PaymentCard(number,
                DataHelper.DateHelper.getDueMonthWithOffset(dueMonthOffset),
                DataHelper.DateHelper.getDueYearWithOffset(dueYearOffset),
                holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(card1WithValidInfo);
        buyPage.isOKStatus();
        assertEquals("APPROVED", DataHelper.DBHelper.getCreditStatus());
    }

    // Тест №4
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard2.csv", numLinesToSkip = 1)
    @DisplayName("(4) Покупка билетов в кредит: корректные данные по карте 2 - ожидание сообщения об отказе")
    void shouldSucceedForValidCard2Input_Credit(String number, String dueMonthOffset, String dueYearOffset, String holder, String CVCCVV) {
        PaymentCard card2WithValidInfo = new PaymentCard(number,
                DataHelper.DateHelper.getDueMonthWithOffset(dueMonthOffset),
                DataHelper.DateHelper.getDueYearWithOffset(dueYearOffset),
                holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(card2WithValidInfo);
        buyPage.isErrorStatus();
        assertEquals("DECLINED", DataHelper.DBHelper.getDirectPurchaseStatus());
    }

    // Тесты №10-14
    @ParameterizedTest
    @CsvFileSource(resources = "/emptyInputForEachField.csv", numLinesToSkip = 1)
    @DisplayName("(10-14) Покупка билетов в кредит: Отсутствие ввода в каждое поле по очереди - ожидание ошибки под полем")
    void shouldShowErrorIfAnyFieldValueIsEmpty_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) {
        PaymentCard cardWithOneEmptyField = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(cardWithOneEmptyField);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }

    // Тест №25
    @CsvFileSource(resources = "/unknownCardNumberInput.csv", numLinesToSkip = 1)
    @DisplayName("(25) Покупка билетов в кредит: Ввод неизвестного для SUT номера карты - ожидание сообщения об отказе")
    void shouldShowErrorIfUnknownCardNumber_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) {
        PaymentCard cardWithOneEmptyField = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(cardWithOneEmptyField);
        buyPage.isErrorStatus();
        assertEquals(0, DataHelper.DBHelper.count());
    }

    // Тесты №26-34
    @ParameterizedTest
    @CsvFileSource(resources = "/invalidInputForEachField.csv", numLinesToSkip = 1)
    @DisplayName("(25-34) Покупка билетов в кредит: Ввод некорректных данных в каждое поле по очереди - ожидание ошибки под полем")
    void shouldShowErrorIfAnyFieldValueIsInvalid_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) {
        PaymentCard cardWithOneInvalidFieldValue = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(cardWithOneInvalidFieldValue);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }

}

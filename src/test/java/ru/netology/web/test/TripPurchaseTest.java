package ru.netology.web.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import ru.netology.web.data.DataHelper;
import ru.netology.web.data.PaymentCard;
import ru.netology.web.page.LandingPage;
import ru.netology.web.page.BuyDirectPage;
import ru.netology.web.page.BuyInCreditPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripPurchaseTest {

    @BeforeEach
    public void openPage() throws SQLException {
        open("http://localhost:8080");
        DataHelper.DBHelper.clear();
    }

    // Тест №1
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard1.csv", numLinesToSkip = 1)
    @DisplayName("(1) Покупка билетов: корректные данные по карте 1 - ожидание сообщения об успехе")
    void shouldSucceedForValidCard1Input_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard card1WithValidInfo = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(card1WithValidInfo);
        buyPage.isOKStatus();
        assertEquals("APPROVED", DataHelper.DBHelper.getDirectPurchaseStatus());
    }

    // Тест №2
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard1.csv", numLinesToSkip = 1)
    @DisplayName("(2) Покупка билетов в кредит: корректные данные по карте 1 - ожидание сообщения об успехе")
    void shouldSucceedForValidCard1Input_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard card1WithValidInfo = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(card1WithValidInfo);
        buyPage.isOKStatus();
        assertEquals("APPROVED", DataHelper.DBHelper.getCreditStatus());
    }

    // Тест №3
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard2.csv", numLinesToSkip = 1)
    @DisplayName("(3) Покупка билетов: корректные данные по карте 2 - ожидание сообщения об отказе")
    void shouldSucceedForValidCard2Input_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard card2WithValidInfo = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(card2WithValidInfo);
        buyPage.isErrorStatus();
        assertEquals("DECLINED", DataHelper.DBHelper.getCreditStatus());
    }

    // Тест №4
    @ParameterizedTest
    @CsvFileSource(resources = "/validInputPurchaseCard2.csv", numLinesToSkip = 1)
    @DisplayName("(4) Покупка билетов в кредит: корректные данные по карте 2 - ожидание сообщения об отказе")
    void shouldSucceedForValidCard2Input_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard card2WithValidInfo = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(card2WithValidInfo);
        buyPage.isErrorStatus();
        assertEquals("DECLINED", DataHelper.DBHelper.getDirectPurchaseStatus());
    }

    // Тесты №5-9
    @ParameterizedTest
    @CsvFileSource(resources = "/emptyInputForEachField.csv", numLinesToSkip = 1)
    @DisplayName("(5-9) Покупка билетов: Отсутствие ввода в каждое поле по очереди - ожидание ошибки под полем")
    void shouldShowErrorIfAnyFieldValueIsEmpty_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard cardWithOneEmptyField = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(cardWithOneEmptyField);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }

    // Тесты №10-14
    @ParameterizedTest
    @CsvFileSource(resources = "/emptyInputForEachField.csv", numLinesToSkip = 1)
    @DisplayName("(10-14) Покупка билетов в кредит: Отсутствие ввода в каждое поле по очереди - ожидание ошибки под полем")
    void shouldShowErrorIfAnyFieldValueIsEmpty_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard cardWithOneEmptyField = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(cardWithOneEmptyField);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }

    // Тест №15
    @CsvFileSource(resources = "/unknownCardNumberInput.csv", numLinesToSkip = 1)
    @DisplayName("(15) Покупка билетов: Ввод неизвестного для SUT номера карты - ожидание сообщения об отказе")
    void shouldShowErrorIfUnknownCardNumber_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
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
    void shouldShowErrorIfAnyFieldValueIsInvalid_DirectPurchase(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard cardWithOneInvalidFieldValue = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyDirectPage buyPage = landing.chooseDirectPurchaseAction();
        buyPage.enterCardData(cardWithOneInvalidFieldValue);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }

    // Тест №25
    @CsvFileSource(resources = "/unknownCardNumberInput.csv", numLinesToSkip = 1)
    @DisplayName("(25) Покупка билетов в кредит: Ввод неизвестного для SUT номера карты - ожидание сообщения об отказе")
    void shouldShowErrorIfUnknownCardNumber_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
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
    void shouldShowErrorIfAnyFieldValueIsInvalid_Credit(String number, String dueMonth, String dueYear, String holder, String CVCCVV) throws SQLException {
        PaymentCard cardWithOneInvalidFieldValue = new PaymentCard(number, dueMonth, dueYear, holder, CVCCVV);
        LandingPage landing = new LandingPage();
        BuyInCreditPage buyPage = landing.chooseCreditAction();
        buyPage.enterCardData(cardWithOneInvalidFieldValue);
        assertTrue(buyPage.isInputInvalid());
        assertEquals(0, DataHelper.DBHelper.count());
    }

}

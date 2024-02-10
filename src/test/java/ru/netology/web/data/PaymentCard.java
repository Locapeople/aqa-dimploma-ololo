package ru.netology.web.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentCard {
    private String cardNumber;
    private String dueMonth;
    private String dueYear;
    private String holder;
    private String CVCCVV;
}

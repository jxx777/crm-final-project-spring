package itschool.crmfinalproject.entity.app.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodEnum {
    CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER, CASH, OTHER,
}
package com.bank.controllers.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TransferPaymentDTO {

    private Double avaiableBalance;
    private Double amountPaid;
    private Boolean isEnabled;
}

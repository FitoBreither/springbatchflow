package com.bank.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transfer_payment_history")
public class TransferPaymentEntity {

    @Id
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "avaiable_balance", nullable = false)
    private Double avaiableBalance;

    @Column(name = "amount_paid", nullable = false)
    private Double amountPaid;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "is_processed", nullable = false)
    private Boolean isProcessed;

    private String error;


}

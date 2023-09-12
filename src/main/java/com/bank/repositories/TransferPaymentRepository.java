package com.bank.repositories;

import com.bank.entities.TransferPaymentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransferPaymentRepository extends CrudRepository<TransferPaymentEntity, String> {

    @Modifying
    @Transactional
    @Query("update TransferPaymentEntity tpe SET tpe.isProcessed = ?1 where tpe.transactionId = ?2")
    void updateTransactionStatus(Boolean newValor, String transactionID);

    @Modifying
    @Transactional
    @Query("update TransferPaymentEntity tpe SET tpe.isProcessed = ?1, tpe.error = ?2 where tpe.transactionId = ?3")
    void updateTransactionStatusError(Boolean newValor, String error, String transactionId);

}

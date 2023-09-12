package com.bank.batch;

import com.bank.repositories.TransferPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CancelTransactionTasklet implements Tasklet {

    @Autowired
    TransferPaymentRepository transferPaymentRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        String transactionId = (String) chunkContext.getStepContext().getJobParameters().get("transactionId");

        String errorMessage = chunkContext.getStepContext()
                        .getStepExecution()
                        .getJobExecution()
                        .getExecutionContext()
                        .getString("message");

        log.error("-----> No se puede procesar la transaccion por el siguiente motivo: ".concat(errorMessage));

        transferPaymentRepository.updateTransactionStatusError(true, errorMessage,transactionId);

        return RepeatStatus.FINISHED;
    }
}

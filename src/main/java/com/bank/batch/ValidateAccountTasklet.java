package com.bank.batch;

import com.bank.entities.TransferPaymentEntity;
import com.bank.repositories.TransferPaymentRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidateAccountTasklet implements Tasklet {

    @Autowired
    private TransferPaymentRepository transferPaymentRepository;


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        Boolean filterIsAproved = true;

        String transactionId = (String) chunkContext.getStepContext().getJobParameters().get("transactionId");

        TransferPaymentEntity transferPayment = transferPaymentRepository.findById(transactionId).orElseThrow();

        if (!transferPayment.getIsEnabled()){
            // Error por que la cuenta esta inactiva
            chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("message","Error, por que la cuenta esta inactiva");
            filterIsAproved = false;
        }

        if (transferPayment.getAmountPaid() > transferPayment.getAvaiableBalance()){
            // Error por saldo insuficiente
            chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("message","Error, el saldo de la cuenta es insuficiente");
            filterIsAproved = false;
        }

        ExitStatus exitStatus = null;
        if (filterIsAproved){
            exitStatus = new ExitStatus("VALID");
            stepContribution.setExitStatus(exitStatus);
        } else {
            exitStatus = new ExitStatus("INVALID");
            stepContribution.setExitStatus(exitStatus);
        }
        return RepeatStatus.FINISHED;
    }
}

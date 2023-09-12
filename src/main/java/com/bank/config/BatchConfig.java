package com.bank.config;

import com.bank.batch.CancelTransactionTasklet;
import com.bank.batch.ProcessPaymentTasklet;
import com.bank.batch.SendNotificationTasklet;
import com.bank.batch.ValidateAccountTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public ValidateAccountTasklet validateAccountTasklet(){
        return new ValidateAccountTasklet();
    }

    @Bean
    public ProcessPaymentTasklet processPaymentTasklet(){
        return new ProcessPaymentTasklet();
    }

    @Bean
    public CancelTransactionTasklet cancelTransactionTasklet(){
        return new CancelTransactionTasklet();
    }

    @Bean
    public SendNotificationTasklet sendNotificationTasklet(){
        return new SendNotificationTasklet();
    }



    @Bean
    @JobScope
    public Step validateAccountStep(){
        return stepBuilderFactory.get("validateAccountStep")
                .tasklet(validateAccountTasklet())
                .build();
    }

    @Bean
    public Step processPaymentStep(){
        return stepBuilderFactory.get("processPaymentStep")
                .tasklet(processPaymentTasklet())
                .build();
    }

    @Bean
    public Step cancelTransactionStep(){
        return stepBuilderFactory.get("cancelTransactionStep")
                .tasklet(cancelTransactionTasklet())
                .build();
    }

    @Bean
    public Step sendNotificationStep(){
        return stepBuilderFactory.get("sendNotificationStep")
                .tasklet(sendNotificationTasklet())
                .build();
    }


    @Bean
    public Job transactionPaymentsJob(){
        return jobBuilderFactory.get("transactionPaymentsJob")
                .start(validateAccountStep())
                    .on("VALID").to(processPaymentStep())
                    .next(sendNotificationStep())

                .from(validateAccountStep())
                    .on("INVALID")
                    .to(cancelTransactionStep())
                    .next(sendNotificationStep())

                .end()
                .build();
    }
}

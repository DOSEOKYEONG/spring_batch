package com.ll.exam.spring_batch.job.productBackup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductBackupJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job productBackupJob(Step productBackupJobStep1, CommandLineRunner initData)throws Exception {
        initData.run();

        return jobBuilderFactory.get("productBackupJob")
                //.incrementer(new RunIdIncrementer()) // 강제로 매번 다른 ID를 파라미터로 시행
                .start(productBackupJobStep1) //스텝을 넣어줘야함
                .build();
    }

    @Bean
    @JobScope
    public Step productBackupJobStep1(Tasklet productBackupJobTasklet) {
        return stepBuilderFactory.get("productBackupJobStep1")
                .tasklet(productBackupJobTasklet)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet productBackupJobTasklet() {
        return (contribution, chunkContext) -> {

            return RepeatStatus.FINISHED;
        };
    }
}
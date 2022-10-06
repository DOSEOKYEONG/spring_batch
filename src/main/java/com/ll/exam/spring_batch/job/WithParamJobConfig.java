package com.ll.exam.spring_batch.job;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WithParamJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job WithParamJob(Step WithParamJobStep1) {
        return jobBuilderFactory.get("withParamJob")
                //.incrementer(new RunIdIncrementer()) // 강제로 매번 다른 ID를 파라미터로 시행
                .start(WithParamJobStep1) //스텝을 넣어줘야함
                .build();
    }

    @Bean
    @JobScope
    public Step WithParamJobStep1(Tasklet WithParamStep1Tasklet) {
        return stepBuilderFactory.get("WithParamJobStep1")
                .tasklet(WithParamStep1Tasklet)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet WithParamStep1Tasklet(
            @Value("#{jobParameters['name']}") String name,
            @Value("#{jobParameters['age']}") Long age
    ) {
        return (contribution, chunkContext) -> {
            System.out.println("WithParam Tasklet");

            return RepeatStatus.FINISHED;
        };
    }
}

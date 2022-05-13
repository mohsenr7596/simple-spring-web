package com.example.simplerest.config;

import com.example.simplerest.dto.post.PostDto;
import com.example.simplerest.dto.user.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);

    @Value("${rest.api.url}")
    private String apiUrl;

    @Bean
    public Step userStep(RestTemplate restTemplate, StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("userStep")
                .<UserDto, UserDto>chunk(10)
                .reader(new StepItemReader<UserDto>(apiUrl + "/users", restTemplate))
                .writer(items -> LOGGER.info(">>>> Writing users: {}", items))
                .build();
    }

    @Bean
    public Step postStep(RestTemplate restTemplate, StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("postStep")
                .<PostDto, PostDto>chunk(10)
                .reader(new StepItemReader<PostDto>(apiUrl + "/posts", restTemplate))
                .writer(items -> LOGGER.info(">>>> Writing posts: {}", items))
                .build();
    }

    @Bean
    public Job exampleJob(JobBuilderFactory jobBuilderFactory,
                          Step userStep,
                          Step postStep) {
        return jobBuilderFactory.get("readFromApiJob")
                .incrementer(new RunIdIncrementer())
                .start(userStep)
                .next(postStep)
                .build();
    }
}

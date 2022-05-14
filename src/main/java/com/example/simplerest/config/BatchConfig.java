package com.example.simplerest.config;

import com.example.simplerest.dto.post.CommentDto;
import com.example.simplerest.dto.post.PostDto;
import com.example.simplerest.dto.todo.TodoDto;
import com.example.simplerest.dto.user.UserDto;
import com.example.simplerest.service.post.CommentService;
import com.example.simplerest.service.post.PostService;
import com.example.simplerest.service.todo.TodoService;
import com.example.simplerest.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);

    private final UserService userService;
    private final CommentService commentService;
    private final PostService postService;
    private final TodoService todoService;


    @Value("${rest.api.url}")
    private String apiUrl;
    private final ObjectMapper mapper;

    public BatchConfig(UserService userService, CommentService commentService, PostService postService, TodoService todoService, ObjectMapper jacksonObjectMapper) {
        this.userService = userService;
        this.commentService = commentService;
        this.postService = postService;
        this.todoService = todoService;
        this.mapper = jacksonObjectMapper;
    }

    @Bean
    public Step userStep(RestTemplate restTemplate, StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("userStep")
                .<UserDto, UserDto>chunk(10)
                .reader(new StepItemReader<UserDto>(apiUrl + "/users", restTemplate, new ParameterizedTypeReference<>() {
                }))
                .writer(items -> {
                    LOGGER.info(">>>> Writing users: {}", items.size());
                    items.forEach(userService::create);
                })
                .build();
    }

    @Bean
    public Step postStep(RestTemplate restTemplate, StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("postStep")
                .<PostDto, PostDto>chunk(10)
                .reader(new StepItemReader<PostDto>(apiUrl + "/posts", restTemplate, new ParameterizedTypeReference<>() {
                }))
                .writer(items -> {
                    LOGGER.info(">>>> Writing posts: {}", items.size());
                    items.forEach(postService::create);
                })
                .build();
    }

    @Bean
    public Step commentStep(RestTemplate restTemplate, StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("commentStep")
                .<CommentDto, CommentDto>chunk(10)
                .reader(new StepItemReader<CommentDto>(apiUrl + "/comments", restTemplate, new ParameterizedTypeReference<>() {
                }))
                .writer(items -> {
                    LOGGER.info(">>>> Writing comments: {}", items.size());
                    items.forEach(commentService::create);
                })
                .build();
    }

    @Bean
    public Step todoStep(RestTemplate restTemplate, StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("todoStep")
                .<TodoDto, TodoDto>chunk(10)
                .reader(new StepItemReader<TodoDto>(apiUrl + "/todos", restTemplate, new ParameterizedTypeReference<>() {
                }))
                .writer(items -> {
                    LOGGER.info(">>>> Writing todos: {}", items.size());
                    items.forEach(todoService::create);
                })
                .build();
    }

    @Bean
    public Job exampleJob(JobBuilderFactory jobBuilderFactory,
                          Step userStep,
                          Step commentStep,
                          Step todoStep,
                          Step postStep) {
        return jobBuilderFactory.get("readFromApiJob")
                .incrementer(new RunIdIncrementer())
                .start(userStep)
                .next(postStep)
                .next(commentStep)
                .next(todoStep)
                .build();
    }
}

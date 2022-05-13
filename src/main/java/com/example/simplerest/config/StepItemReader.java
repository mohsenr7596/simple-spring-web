package com.example.simplerest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class StepItemReader<T> implements ItemReader<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StepItemReader.class);

    private final String apiUrl;
    private final RestTemplate restTemplate;
    private List<T> data;
    private int nextIndex = 0;

    public StepItemReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public T read() {
        LOGGER.info("Reading the information of the next data");

        if (CollectionUtils.isEmpty(data)) {
            data = fetchFromApi();
        }

        T nextData = null;
        if (nextIndex < data.size()) {
            nextData = data.get(nextIndex);
            nextIndex++;
        } else {
            nextIndex = 0;
            data = null;
        }
        LOGGER.info("Found data: {}", nextData);
        return nextData;
    }

    private List<T> fetchFromApi() {
        LOGGER.debug("Fetching data from an external API by using the url: {}", apiUrl);

        var entity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<T>>() {
        });
        var responseData = entity.getBody();
        LOGGER.debug("Found {} data", responseData.size());

        return responseData;
    }
}
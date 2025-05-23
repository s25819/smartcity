package pl.edu.pjatk.s25819.smartcity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class SensorsConfig {

    @Value("${smartcity.sensors.base-url}")
    private String sensorBaseUrl;

    @Bean
    public RestTemplate sensorsRestTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new LoggingInterceptor());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(sensorBaseUrl));
        return restTemplate;
    }

    @Bean
    WebClient sensorsWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(sensorBaseUrl)
                .build();
    }
}

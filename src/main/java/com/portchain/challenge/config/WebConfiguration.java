package com.portchain.challenge.config;

import com.portchain.challenge.format.PercentileFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final ApplicationProperties applicationProperties;

    public WebConfiguration(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public RestTemplate portchainRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(applicationProperties.getPortchain().getUrl()));
        return restTemplate;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PercentileFormatter());
    }
}

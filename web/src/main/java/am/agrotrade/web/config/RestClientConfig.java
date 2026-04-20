package am.agrotrade.web.config;

import am.agrotrade.core.properties.RestProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate(RestProperties props) {

        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(props.connectTimeout());
        factory.setReadTimeout(props.readTimeout());

        return new RestTemplate(factory);
    }
}
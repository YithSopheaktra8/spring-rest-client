package co.istad.restfull.config;

import co.istad.restfull.httpInterface.CategoryService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@Slf4j
public class HttpInterfaceClient {

    @Bean
    public CategoryService categoryServiceClient() {
        WebClient webClient = WebClient.builder()
                .defaultStatusHandler(HttpStatusCode::isError, (response) -> {
                    log.error("Error: {}" , response.statusCode());
                    log.error("header: {}" , response.headers());
                    switch (response.statusCode()) {
                        case HttpStatus.NOT_FOUND:
                            log.error("Not found");
                            break;
                        case HttpStatus.INTERNAL_SERVER_ERROR:
                            log.error("Internal server error");
                            break;
                        case HttpStatus.BAD_REQUEST:
                            log.error("Bad request");
                            break;
                        default:
                            log.error("Unknown error");
                    }
                    return null;
                })
                .build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(CategoryService.class);
    }

//    @Bean
//    public CategoryService categoryServiceClient2() {
//
//        RestClient restClient = RestClient.builder().baseUrl("https://api.github.com/").build();
//        RestClientAdapter adapter = RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//         return  factory.createClient(CategoryService.class);
//    }
//
//    @Bean
//    public CategoryService categoryServiceClient3() {
//
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://api.github.com/"));
//        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        return factory.createClient(CategoryService.class);
//    }

}

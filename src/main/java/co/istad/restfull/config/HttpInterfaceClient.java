package co.istad.restfull.config;

import co.istad.restfull.httpInterface.CategoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class HttpInterfaceClient {

    @Bean
    public CategoryService categoryServiceClient() {

        WebClient webClient = WebClient.builder().baseUrl("https://api.github.com/")
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

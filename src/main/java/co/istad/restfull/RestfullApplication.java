package co.istad.restfull;

import co.istad.restfull.domain.Category;
import co.istad.restfull.domain.Post;
import co.istad.restfull.httpInterface.CategoryService;
import co.istad.restfull.restClient.RestClientServiceImpl;
import co.istad.restfull.webclient.WebClientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class RestfullApplication implements CommandLineRunner {

    private final RestClientServiceImpl restClientService;
    private final WebClientServiceImpl webClientService;
    private final CategoryService categoryService;

    public static void main(String[] args) {
        SpringApplication.run(RestfullApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Post post = Post.builder()
                .userId(1)
                .id(1)
                .title("title")
                .body("body")
                .build();

        categoryService.getCategories().subscribe(categories -> {
            log.info("categories: {}", categories);
        });


        Category category = categoryService.getCategoryById(5);
        log.info("category synchronous: {}", category);


//        ResponseEntity<Category> categoryResponseEntity = categoryService.deleteCategory(1);
//        log.info("categoryResponseEntity: {}", categoryResponseEntity);
//        log.info("categoryResponseEntity body: {}", categoryResponseEntity.getBody());
//        log.info("categoryResponseEntity status code: {}", categoryResponseEntity.getStatusCode());
    }
}

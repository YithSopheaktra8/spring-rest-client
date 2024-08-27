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
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

        // asynchronous
//        categoryService.getCategories().subscribe(categories -> {
//            log.info("categories: {}", categories);
//        });

//        restClientService.uploadFile();
        // synchronous
//       try{
//           Category category = categoryService.getCategoryById(111111);
//           log.info("category synchronous: {}", category);
//       }catch (Exception e){
//           log.error("Error: {}", e.getMessage());
//       }

        MultiValueMap<String, Object> file = new LinkedMultiValueMap<>();
        file.add("file", new FileSystemResource("src/main/resources/static/pkce.png"));

        ResponseEntity<?> response = categoryService.uploadFile(file);
        log.info("response: {}", response);
        log.info("response: {}", response.getStatusCode());
    }
}

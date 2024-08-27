package co.istad.restfull.webclient;

import co.istad.restfull.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class WebClientServiceImpl {

    private final WebClient webClient;

    public WebClientServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    public void getPosts() {
        Post[] posts = webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToMono(Post[].class)
                .block();

        for (Post post : posts) {
            log.info(post.toString());
        }
    }

    public void getPostById(Integer id) {
        Post post = webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(Post.class)
                .block();

        log.info(post.toString());
    }

}

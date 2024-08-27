package co.istad.restfull.restClient;

import co.istad.restfull.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
public class RestClientServiceImpl {

    private final RestClient restClient;

    public RestClientServiceImpl(RestClient.Builder restClient) {
        this.restClient = restClient
                .build();
    }

    public void getPosts() {
        Post[] posts = restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .retrieve()
                .body(Post[].class);
        for (Post post : posts) {
            System.out.println(post.toString());
        }
    }

    public void getPostById(Integer id) {
        ResponseEntity<Post> post = restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/{id}", id)
                .retrieve()
                .toEntity(Post.class);

        log.info("post body: {}", post.getBody());
        log.info("post headers: {}", post.getHeaders());
        log.info("post status code: {}", post.getStatusCode());

    }

    public void createPost(Post post) {

        ResponseEntity<?> response = restClient.post()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .retrieve()
                .toBodilessEntity();

        log.info("post body: {}", response.getBody());
        log.info("post headers: {}", response.getHeaders());
        log.info("post status code: {}", response.getStatusCode());
    }

    public void updatePost(Post post) {
        ResponseEntity<?> response = restClient.put()
                .uri("https://jsonplaceholder.typicode.com/posts/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .retrieve()
                .toEntity(Post.class);

        log.info("post body: {}", response.getBody());
        log.info("post headers: {}", response.getHeaders());
        log.info("post status code: {}", response.getStatusCode());
    }

    public void uploadFile() {

        MultiValueMap<String, Object> file = new LinkedMultiValueMap<>();
        file.add("file", new FileSystemResource("src/main/resources/static/pkce.png"));

        ResponseEntity<?> response = restClient.post()
                .uri("https://api.escuelajs.co/api/v1/files/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(file)
                .retrieve()
                .toEntity(String.class);

        log.info("post body: {}", response.getBody());
        log.info("post headers: {}", response.getHeaders());
        log.info("post status code: {}", response.getStatusCode());
        log.info("post response: {}", response);

    }

    public void errorHandling() {
//        ResponseEntity<Post> post = restClient.get()
//                .uri("https://jsonplaceholder.typicode.com/posts/asd")
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, (request, response) ->{
//                    log.info("request error {}", request.getURI());
//                    log.info("response error {}", response.getStatusCode());
//                })
//                .toEntity(Post.class);

        ResponseEntity<Post> post = restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/asd")
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) ->{
                    switch (response.getStatusCode()){
                        case BAD_REQUEST:
                            log.info("bad request");
                            break;
                        case NOT_FOUND:
                            log.info("not found");
                            break;
                        default:
                            log.info("default");
                    }
                })
                .toEntity(Post.class);

    }

    public void errorHandlingWithExchange(Integer id) {
        Post post = restClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts/{id}", id)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        log.info("request error {}", request.getURI());
                        log.info("response error {}", response.getStatusCode());
                    }

                    return response.bodyTo(Post.class);
                });

        log.info("post: {}", post.toString());

    }
}

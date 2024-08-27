package co.istad.restfull.httpInterface;

import co.istad.restfull.domain.Category;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CategoryService {

    @GetExchange("https://api.escuelajs.co/api/v1/categories/{categoryId}")
    Category getCategoryById(@PathVariable Integer categoryId);

    @GetExchange("https://api.escuelajs.co/api/v1/categories")
    Flux<Category> getCategories();

    @PostExchange(value = "https://api.escuelajs.co/api/v1/categories", contentType = "application/json")
    Category createCategory(@RequestBody Category category);

    @PutExchange("https://api.escuelajs.co/api/v1/categories/{categoryId}")
    Category updateCategory(@PathVariable Integer categoryId, @RequestBody Category category);

    @DeleteExchange("https://jsonplaceholder.typicode.com/posts/{postId}")
    ResponseEntity<Category> deleteCategory(@PathVariable Integer postId);

    @PostExchange("https://api.escuelajs.co/api/v1/files/upload")
    ResponseEntity<?> uploadFile(@RequestPart MultiValueMap<String, Object> file);
}

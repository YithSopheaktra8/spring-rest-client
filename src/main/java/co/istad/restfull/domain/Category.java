package co.istad.restfull.domain;

import lombok.*;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Category  {
    private Integer id;
    private String name;
    private String image;
    private LocalDateTime creationAt;
    private LocalDateTime updatedAt;

}

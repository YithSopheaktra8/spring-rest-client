package co.istad.restfull.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Post {

    private Integer userId;
    private Integer id;
    private String title;
    private String body;

}

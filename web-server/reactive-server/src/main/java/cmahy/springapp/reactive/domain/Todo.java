package cmahy.springapp.reactive.domain;

import lombok.Data;

@Data
public class Todo {

    private Long id;

    private String title;

    private String description;
}

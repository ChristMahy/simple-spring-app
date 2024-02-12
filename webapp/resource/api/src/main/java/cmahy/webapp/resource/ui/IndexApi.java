package cmahy.webapp.resource.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = { "", "/" })
public interface IndexApi {

    @GetMapping
    String index();
}

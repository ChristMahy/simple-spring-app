package cmahy.webapp.resource.impl.adapter.ui;

import cmahy.webapp.resource.ui.IndexApi;
import org.springframework.stereotype.Controller;

@Controller
public class IndexApiImpl implements IndexApi {

    @Override
    public String index() {
        return "index";
    }
}

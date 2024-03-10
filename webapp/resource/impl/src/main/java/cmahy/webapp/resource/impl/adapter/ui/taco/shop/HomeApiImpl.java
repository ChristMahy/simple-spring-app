package cmahy.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.webapp.resource.ui.taco.shop.HomeApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class HomeApiImpl implements HomeApi {

    private static final Logger LOG = LoggerFactory.getLogger(HomeApiImpl.class);

    @Override
    public String home() {
        LOG.debug("Go to home page");

        return "taco-shop/home";
    }
}

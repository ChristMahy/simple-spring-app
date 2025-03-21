package cmahy.simple.spring.webapp.resource.impl.adapter.ui.taco.shop;

import cmahy.simple.spring.webapp.resource.ui.taco.shop.HomeUi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class HomeUiImpl implements HomeUi {

    private static final Logger LOG = LoggerFactory.getLogger(HomeUiImpl.class);

    @Override
    public String home() {
        LOG.debug("Go to home page");

        return "taco-shop/home";
    }
}

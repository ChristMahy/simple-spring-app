package cmahy.simple.spring.security.common.impl.io;

import cmahy.simple.spring.security.common.impl.rsa.exception.ResourceNotFoundException;
import cmahy.simple.spring.security.common.impl.rsa.exception.UnreachableResourceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.net.MalformedURLException;
import java.util.Objects;

public class ResourceFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceFactory.class);

    private final ResourceLoader resourceLoader;

    public ResourceFactory(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource create(String location) throws UnreachableResourceException, ResourceNotFoundException {
        LOG.info("Trying to resolve resource <{}>", location);

        if (isUnreachableResource(location)) {
            LOG.warn("Unreachable resource <{}>, expected values; <classpath:YOUR_LOCATION> or URL protocol (eg.: <file:/YOUR_LOCATION>, ...)", location);

            throw new UnreachableResourceException(String.format("Unreachable resource <%s>", location));
        }

        Resource resource = resourceLoader.getResource(location);

        if (!resource.exists()) {
            LOG.debug("Resource <{}> does not exist", location);

            throw new ResourceNotFoundException(String.format("Resource <%s> does not exist", location));
        }

        LOG.debug("Resource <{}> exists", location);

        return resource;
    }

    private boolean isUnreachableResource(String location) {
        try {
            return !StringUtils.startsWith(location, ResourceLoader.CLASSPATH_URL_PREFIX) &&
                Objects.isNull(ResourceUtils.toURL(location).getProtocol());
        } catch (MalformedURLException e) {
            if (e.getMessage().startsWith("no protocol")) {
                return true;
            }

            return false;
        }
    }
}

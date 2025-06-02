package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.impl.rsa.exception.*;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class NormalizedKeyResolverRepository {

    private static final Logger LOG = LoggerFactory.getLogger(NormalizedKeyResolverRepository.class);

    private final ResourceLoader resourceLoader;

    public NormalizedKeyResolverRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public byte[] execute(String location) throws FileResolverException {
        LOG.info("Trying to resolve resource <{}>", location);

        if (isUnreachableResource(location)) {
            LOG.warn("Unreachable resource <{}>, expected values; <classpath> or URL protocol (eg.: <file:/YOUR_LOCATION>, ...)", location);

            throw new UnreachableResourceException(String.format("Unreachable resource <%s>", location));
        }

        Resource resource = resourceLoader.getResource(location);

        if (!resource.exists()) {
            LOG.debug("Resource <{}> does not exist", location);

            throw new ResourceNotFoundException(String.format("Resource <%s> does not exist", location));
        }

        LOG.debug("Resource <{}> exists", location);

        try (var inStream = resource.getInputStream()) {

            String normalized = StreamUtils.copyToString(inStream, StandardCharsets.UTF_8);

            normalized = RegExUtils.replacePattern(normalized, "-----BEGIN (PUBLIC|PRIVATE) KEY-----", "");
            normalized = RegExUtils.replacePattern(normalized, "-----END (PUBLIC|PRIVATE) KEY-----", "");
            normalized = RegExUtils.replacePattern(normalized, "\\s", "");

            return Base64.getDecoder().decode(normalized);

        } catch (IOException ioException) {
            LOG.error("Error reading resource <{}>", location, ioException);

            throw new IOResourceException(String.format("Unreadable resource <%s>", location));
        }
    }

    private boolean isUnreachableResource(String location) {
        try {
            return !StringUtils.startsWith(location, ResourceLoader.CLASSPATH_URL_PREFIX) &&
                Objects.isNull(ResourceUtils.toURL(location).getProtocol());
        } catch (MalformedURLException e) {
            return false;
        }
    }
}

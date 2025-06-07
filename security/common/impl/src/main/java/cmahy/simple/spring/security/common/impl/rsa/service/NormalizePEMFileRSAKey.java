package cmahy.simple.spring.security.common.impl.rsa.service;

import cmahy.simple.spring.security.common.impl.rsa.exception.IllegalIOContentException;
import org.apache.commons.lang3.RegExUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class NormalizePEMFileRSAKey {

    private static final Logger LOG = LoggerFactory.getLogger(NormalizePEMFileRSAKey.class);

    public byte[] execute(byte[] key) throws IllegalIOContentException {

        LOG.info("Normalizing PEM file key");

        if (Objects.isNull(key)) {
            LOG.debug("PEM file content is null");

            throw new IllegalIOContentException("Content has not to be null");
        }

        String normalizeKey = new String(key, StandardCharsets.UTF_8);

        normalizeKey = RegExUtils.replacePattern(normalizeKey, "-----BEGIN (PUBLIC|PRIVATE) KEY-----", "");
        normalizeKey = RegExUtils.replacePattern(normalizeKey, "-----END (PUBLIC|PRIVATE) KEY-----", "");
        normalizeKey = RegExUtils.replacePattern(normalizeKey, "\\s", "");

        LOG.debug("Base64 decode PEM file key");

        return Base64.getDecoder().decode(normalizeKey);

    }

}

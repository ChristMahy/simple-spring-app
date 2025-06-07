package cmahy.simple.spring.security.common.impl.rsa.factory;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.*;
import java.util.Objects;

public class RSAPrivateKeyFactory {

    private static final Logger LOG = LoggerFactory.getLogger(RSAPrivateKeyFactory.class);

    public RSAPrivateKey create(byte[] rsaKey) throws RSAPrivateKeyException {

        LOG.info("Creating RSA Private Key");

        if (Objects.isNull(rsaKey)) {
            LOG.debug("RSA Key is null");

            throw new RSAPrivateKeyException("RSA key is null");
        }

        try {

            EncodedKeySpec spec = new PKCS8EncodedKeySpec(rsaKey);

            return (RSAPrivateKey) KeyFactory
                .getInstance("RSA")
                .generatePrivate(spec);

        } catch (
            NoSuchAlgorithmException |
            InvalidKeySpecException
                e
        ) {
            LOG.debug("Creating RSA Private Key failed", e);

            throw new RSAPrivateKeyException(e.getMessage(), e);
        }

    }
}

package cmahy.simple.spring.security.common.impl.rsa.factory;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPublicKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.Objects;

public class RSAPublicKeyFactory {

    private static final Logger LOG = LoggerFactory.getLogger(RSAPublicKeyFactory.class);

    public RSAPublicKey create(byte[] rsaKey) throws RSAPublicKeyException {

        LOG.info("Creating RSA Public Key");

        if (Objects.isNull(rsaKey)) {
            LOG.debug("RSA Key is null");

            throw new RSAPublicKeyException("RSA key is null");
        }

        try {

            EncodedKeySpec spec = new X509EncodedKeySpec(rsaKey);

            return (RSAPublicKey) KeyFactory
                .getInstance("RSA")
                .generatePublic(spec);

        } catch (
            NoSuchAlgorithmException |
            InvalidKeySpecException
                e
        ) {
            LOG.debug("Creating RSA Public Key failed", e);

            throw new RSAPublicKeyException(e.getMessage(), e);
        }

    }
}

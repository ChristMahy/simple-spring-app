package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;
import cmahy.simple.spring.security.common.impl.rsa.exception.FileResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.*;
import java.util.*;

public class RSAPrivateKeyFileResolverRepositoryImpl implements RSAPrivateKeyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RSAPrivateKeyFileResolverRepositoryImpl.class);

    private final NormalizedKeyResolverRepository normalizedKeyResolverRepository;
    private final RSAPrivateKeyConfigurationRepository rsaPrivateKeyConfigurationRepository;

    public RSAPrivateKeyFileResolverRepositoryImpl(
        NormalizedKeyResolverRepository normalizedKeyResolverRepository,
        RSAPrivateKeyConfigurationRepository rsaPrivateKeyConfigurationRepository
    ) {
        this.normalizedKeyResolverRepository = normalizedKeyResolverRepository;
        this.rsaPrivateKeyConfigurationRepository = rsaPrivateKeyConfigurationRepository;
    }

    @Override
    public Map<PrivateKeyId, RSAPrivateKey> findAll() throws RSAPrivateKeyException {

        LOG.info("Get all private keys");

        Map<PrivateKeyId, String> privateKeyIds = rsaPrivateKeyConfigurationRepository.allLocations();

        Map<PrivateKeyId, RSAPrivateKey> privateKeys = new HashMap<>();

        for (PrivateKeyId privateKeyId : privateKeyIds.keySet()) {
            privateKeys.put(privateKeyId, findById(privateKeyId));
        }

        LOG.debug("Found <{}> private keys", privateKeys.size());

        return Collections.unmodifiableMap(privateKeys);
    }

    @Override
    public RSAPrivateKey findById(PrivateKeyId id) throws RSAPrivateKeyException {

        try {

            Optional<String> location = rsaPrivateKeyConfigurationRepository.getLocation(id);

            LOG.info("Location <{}> matches for <{}>", location.orElse("NONE"), id);

            if (location.isEmpty()) {
                throw new RSAPrivateKeyException("No location found");
            }

            byte[] normalizedKey = normalizedKeyResolverRepository.execute(location.get());

            LOG.debug("Key successfully loaded from <{}>", location.get());

            EncodedKeySpec spec = new PKCS8EncodedKeySpec(normalizedKey);

            return (RSAPrivateKey) KeyFactory
                .getInstance("RSA")
                .generatePrivate(spec);

        } catch (
            FileResolverException |
            NoSuchAlgorithmException |
            InvalidKeySpecException
                e
        ) {
            throw new RSAPrivateKeyException(e.getMessage(), e);
        }

    }

}

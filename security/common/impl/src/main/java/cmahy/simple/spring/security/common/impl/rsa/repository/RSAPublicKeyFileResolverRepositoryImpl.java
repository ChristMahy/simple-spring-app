package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPublicKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;
import cmahy.simple.spring.security.common.impl.rsa.exception.FileResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;

public class RSAPublicKeyFileResolverRepositoryImpl implements RSAPublicKeyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RSAPublicKeyFileResolverRepositoryImpl.class);

    private final NormalizedKeyResolverRepository normalizedKeyResolverRepository;
    private final RSAPublicKeyConfigurationRepository rsaPublicKeyConfigurationRepository;

    public RSAPublicKeyFileResolverRepositoryImpl(
        NormalizedKeyResolverRepository normalizedKeyResolverRepository,
        RSAPublicKeyConfigurationRepository rsaPublicKeyConfigurationRepository
    ) {
        this.normalizedKeyResolverRepository = normalizedKeyResolverRepository;
        this.rsaPublicKeyConfigurationRepository = rsaPublicKeyConfigurationRepository;
    }

    @Override
    public Map<PublicKeyId, RSAPublicKey> findAll() throws RSAPublicKeyException {

        LOG.info("Get all public keys");

        Map<PublicKeyId, String> publicKeyIds = rsaPublicKeyConfigurationRepository.allLocations();

        Map<PublicKeyId, RSAPublicKey> publicKeys = new HashMap<>();

        for (PublicKeyId publicKeyId : publicKeyIds.keySet()) {
            publicKeys.put(publicKeyId, findById(publicKeyId));
        }

        LOG.debug("Found <{}> public keys", publicKeys.size());

        return Collections.unmodifiableMap(publicKeys);

    }

    @Override
    public RSAPublicKey findById(PublicKeyId id) throws RSAPublicKeyException {

        try {

            Optional<String> location = rsaPublicKeyConfigurationRepository.getLocation(id);

            LOG.info("Location <{}> matches for <{}>", location.orElse("NONE"), id);

            if (location.isEmpty()) {
                throw new RSAPublicKeyException("No location found");
            }

            byte[] normalizedKey = normalizedKeyResolverRepository.execute(location.get());

            LOG.debug("Key successfully loaded from <{}>", location.get());

            EncodedKeySpec spec = new X509EncodedKeySpec(normalizedKey);

            return (RSAPublicKey) KeyFactory
                .getInstance("RSA")
                .generatePublic(spec);

        } catch (
            FileResolverException |
            NoSuchAlgorithmException |
            InvalidKeySpecException
                e
        ) {
            throw new RSAPublicKeyException(e.getMessage(), e);
        }
    }

}

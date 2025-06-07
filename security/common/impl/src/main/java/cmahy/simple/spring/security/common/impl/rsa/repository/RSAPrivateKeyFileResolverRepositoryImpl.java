package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;
import cmahy.simple.spring.security.common.impl.io.ResourceFactory;
import cmahy.simple.spring.security.common.impl.rsa.exception.FileResolverException;
import cmahy.simple.spring.security.common.impl.rsa.exception.IOResourceException;
import cmahy.simple.spring.security.common.impl.rsa.factory.RSAPrivateKeyFactory;
import cmahy.simple.spring.security.common.impl.rsa.service.NormalizePEMFileRSAKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;

public class RSAPrivateKeyFileResolverRepositoryImpl implements RSAPrivateKeyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RSAPrivateKeyFileResolverRepositoryImpl.class);

    private final ResourceFactory resourceFactory;
    private final NormalizePEMFileRSAKey normalizePEMFileRsaKey;
    private final RSAPrivateKeyFactory rsaPrivateKeyFactory;
    private final RSAPrivateKeyConfigurationRepository rsaPrivateKeyConfigurationRepository;

    public RSAPrivateKeyFileResolverRepositoryImpl(
        ResourceFactory resourceFactory,
        NormalizePEMFileRSAKey normalizePEMFileRsaKey,
        RSAPrivateKeyFactory rsaPrivateKeyFactory,
        RSAPrivateKeyConfigurationRepository rsaPrivateKeyConfigurationRepository
    ) {
        this.resourceFactory = resourceFactory;
        this.normalizePEMFileRsaKey = normalizePEMFileRsaKey;
        this.rsaPrivateKeyFactory = rsaPrivateKeyFactory;
        this.rsaPrivateKeyConfigurationRepository = rsaPrivateKeyConfigurationRepository;
    }

    @Override
    public Map<PrivateKeyId, RSAPrivateKey> findAll() throws RSAPrivateKeyException {

        LOG.info("Get all private keys");

        Map<PrivateKeyId, String> privateKeyIds = rsaPrivateKeyConfigurationRepository.allLocations();

        if (Objects.isNull(privateKeyIds) || privateKeyIds.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<PrivateKeyId, RSAPrivateKey> privateKeys = new HashMap<>();

        for (Map.Entry<PrivateKeyId, String> privateKey : privateKeyIds.entrySet()) {
            privateKeys.put(privateKey.getKey(), map(privateKey.getValue()));
        }

        LOG.debug("Found <{}> private keys", privateKeys.size());

        return Collections.unmodifiableMap(privateKeys);
    }

    @Override
    public RSAPrivateKey findById(PrivateKeyId id) throws RSAPrivateKeyException {

        Optional<String> location = rsaPrivateKeyConfigurationRepository.getLocation(id);

        LOG.info("Location <{}> matches for <{}>", location.orElse("NONE"), id);

        if (location.isEmpty()) {
            throw new RSAPrivateKeyException("No location found");
        }

        return map(location.get());
    }

    private RSAPrivateKey map(String location) throws RSAPrivateKeyException {
        try {
            Resource resource = resourceFactory.create(location);

            try (var inStream = resource.getInputStream()) {

                byte[] resourceContent = inStream.readAllBytes();

                LOG.debug("Resource successfully loaded from <{}>", location);

                byte[] normalizedResourceContent = normalizePEMFileRsaKey.execute(resourceContent);

                return rsaPrivateKeyFactory.create(normalizedResourceContent);

            } catch (IOException ioException) {
                LOG.error("Error reading resource <{}>", resource, ioException);

                throw new IOResourceException(String.format("Unreadable resource <%s>", resource));
            }
        } catch (FileResolverException e) {
            throw new RSAPrivateKeyException(e.getMessage(), e);
        }
    }

}

package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.exception.RSAPublicKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;
import cmahy.simple.spring.security.common.impl.io.ResourceFactory;
import cmahy.simple.spring.security.common.impl.rsa.exception.FileResolverException;
import cmahy.simple.spring.security.common.impl.rsa.exception.IOResourceException;
import cmahy.simple.spring.security.common.impl.rsa.factory.RSAPublicKeyFactory;
import cmahy.simple.spring.security.common.impl.rsa.service.NormalizePEMFileRSAKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class RSAPublicKeyFileResolverRepositoryImpl implements RSAPublicKeyRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RSAPublicKeyFileResolverRepositoryImpl.class);

    private final ResourceFactory resourceFactory;
    private final NormalizePEMFileRSAKey normalizePEMFileRsaKey;
    private final RSAPublicKeyFactory rsaPublicKeyFactory;
    private final RSAPublicKeyConfigurationRepository rsaPublicKeyConfigurationRepository;

    public RSAPublicKeyFileResolverRepositoryImpl(
        ResourceFactory resourceFactory,
        NormalizePEMFileRSAKey normalizePEMFileRsaKey,
        RSAPublicKeyFactory rsaPublicKeyFactory,
        RSAPublicKeyConfigurationRepository rsaPublicKeyConfigurationRepository
    ) {
        this.resourceFactory = resourceFactory;
        this.normalizePEMFileRsaKey = normalizePEMFileRsaKey;
        this.rsaPublicKeyFactory = rsaPublicKeyFactory;
        this.rsaPublicKeyConfigurationRepository = rsaPublicKeyConfigurationRepository;
    }

    @Override
    public Map<PublicKeyId, RSAPublicKey> findAll() throws RSAPublicKeyException {

        LOG.info("Get all public keys");

        Map<PublicKeyId, String> publicKeyIds = rsaPublicKeyConfigurationRepository.allLocations();

        if (Objects.isNull(publicKeyIds) || publicKeyIds.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<PublicKeyId, RSAPublicKey> publicKeys = new HashMap<>();

        for (Map.Entry<PublicKeyId, String> publicKey : publicKeyIds.entrySet()) {
            publicKeys.put(publicKey.getKey(), map(publicKey.getValue()));
        }

        LOG.debug("Found <{}> public keys", publicKeys.size());

        return Collections.unmodifiableMap(publicKeys);

    }

    @Override
    public RSAPublicKey findById(PublicKeyId id) throws RSAPublicKeyException {

        Optional<String> location = rsaPublicKeyConfigurationRepository.getLocation(id);

        LOG.info("Location <{}> matches for <{}>", location.orElse("NONE"), id);

        if (location.isEmpty()) {
            throw new RSAPublicKeyException("No location found");
        }

        return map(location.get());
    }

    private RSAPublicKey map(String location) throws RSAPublicKeyException {
        try {
            Resource resource = resourceFactory.create(location);

            try (var inStream = resource.getInputStream()) {

                byte[] resourceContent = inStream.readAllBytes();

                LOG.debug("Resource successfully loaded from <{}>", location);

                byte[] normalizedResourceContent = normalizePEMFileRsaKey.execute(resourceContent);

                return rsaPublicKeyFactory.create(normalizedResourceContent);

            } catch (IOException ioException) {
                LOG.error("Error reading resource <{}>", resource, ioException);

                throw new IOResourceException(String.format("Unreadable resource <%s>", resource));
            }
        } catch (FileResolverException e) {
            throw new RSAPublicKeyException(e.getMessage(), e);
        }
    }

}

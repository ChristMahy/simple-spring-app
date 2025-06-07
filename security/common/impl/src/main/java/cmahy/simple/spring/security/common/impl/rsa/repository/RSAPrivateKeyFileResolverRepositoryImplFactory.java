package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyConfigurationRepository;
import cmahy.simple.spring.security.common.impl.io.ResourceFactory;
import cmahy.simple.spring.security.common.impl.rsa.factory.RSAPrivateKeyFactory;
import cmahy.simple.spring.security.common.impl.rsa.service.NormalizePEMFileRSAKey;
import org.springframework.core.io.ResourceLoader;

public final class RSAPrivateKeyFileResolverRepositoryImplFactory {

    private RSAPrivateKeyFileResolverRepositoryImplFactory() {}

    public static RSAPrivateKeyFileResolverRepositoryImpl create(
        ResourceLoader resourceLoader,
        RSAPrivateKeyConfigurationRepository rsaPrivateKeyConfigurationRepository
    ) {
        return new RSAPrivateKeyFileResolverRepositoryImpl(
            new ResourceFactory(resourceLoader),
            new NormalizePEMFileRSAKey(),
            new RSAPrivateKeyFactory(),
            rsaPrivateKeyConfigurationRepository
        );
    }

}

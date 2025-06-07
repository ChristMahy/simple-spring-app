package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyConfigurationRepository;
import cmahy.simple.spring.security.common.impl.io.ResourceFactory;
import cmahy.simple.spring.security.common.impl.rsa.factory.RSAPublicKeyFactory;
import cmahy.simple.spring.security.common.impl.rsa.service.NormalizePEMFileRSAKey;
import org.springframework.core.io.ResourceLoader;

public final class RSAPublicKeyFileResolverRepositoryImplFactory {

    private RSAPublicKeyFileResolverRepositoryImplFactory() {}

    public static RSAPublicKeyFileResolverRepositoryImpl create(
        ResourceLoader resourceLoader,
        RSAPublicKeyConfigurationRepository rsaPublicKeyConfigurationRepository
    ) {
        return new RSAPublicKeyFileResolverRepositoryImpl(
            new ResourceFactory(resourceLoader),
            new NormalizePEMFileRSAKey(),
            new RSAPublicKeyFactory(),
            rsaPublicKeyConfigurationRepository
        );
    }

}

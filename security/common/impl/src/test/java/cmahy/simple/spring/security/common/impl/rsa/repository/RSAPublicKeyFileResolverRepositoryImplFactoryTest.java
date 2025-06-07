package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class RSAPublicKeyFileResolverRepositoryImplFactoryTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private RSAPublicKeyConfigurationRepository rsaPublicKeyConfigurationRepository;

    @Test
    void create() {
        assertDoesNotThrow(() -> {


            RSAPublicKeyFileResolverRepositoryImpl actual = RSAPublicKeyFileResolverRepositoryImplFactory.create(
                resourceLoader, rsaPublicKeyConfigurationRepository
            );


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RSAPublicKeyRepository.class);
        });
    }
}
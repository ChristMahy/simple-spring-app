package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class RSAPrivateKeyFileResolverRepositoryImplFactoryTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private RSAPrivateKeyConfigurationRepository rsaPrivateKeyConfigurationRepository;

    @Test
    void create() {
        assertDoesNotThrow(() -> {


            RSAPrivateKeyFileResolverRepositoryImpl actual = RSAPrivateKeyFileResolverRepositoryImplFactory.create(
                resourceLoader, rsaPrivateKeyConfigurationRepository
            );


            assertThat(actual)
                .isNotNull()
                .isInstanceOf(RSAPrivateKeyRepository.class);
        });
    }
}
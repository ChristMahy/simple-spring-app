package cmahy.simple.spring.security.common.impl.rsa.factory;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RSAPrivateKeyFactoryTest {

    @InjectMocks
    private RSAPrivateKeyFactory rsaPrivateKeyFactory;

    @Mock
    private KeyFactory keyFactory;

    @Mock
    private RSAPrivateKey privateKey;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<KeyFactory> keyFactoryMocked = Mockito.mockStatic(KeyFactory.class)) {
                keyFactoryMocked.when(() -> KeyFactory.getInstance("RSA")).thenReturn(keyFactory);
                when(keyFactory.generatePrivate(any(PKCS8EncodedKeySpec.class))).thenReturn(privateKey);

                byte[] randomBytes = Generator.randomBytes(Generator.randomInt(500, 1000));


                RSAPrivateKey actual = rsaPrivateKeyFactory.create(randomBytes);


                assertThat(actual).isSameAs(privateKey);
            }
        });
    }

    @Test
    void create_givenBytesShouldNeverBeNull() {
        assertThrows(RSAPrivateKeyException.class,() -> {


            rsaPrivateKeyFactory.create(null);


        });
    }

    @Test
    void create_onAnyException_thenThrowRSAPublicKeyException() {
        Stream.of(
            NoSuchAlgorithmException.class,
            InvalidKeySpecException.class
        )
            .forEach(exception -> {
                assertThrows(RSAPrivateKeyException.class, () -> {
                    try (MockedStatic<KeyFactory> keyFactoryMocked = Mockito.mockStatic(KeyFactory.class)) {
                        keyFactoryMocked.when(() -> KeyFactory.getInstance("RSA")).thenReturn(keyFactory);
                        when(keyFactory.generatePrivate(any(PKCS8EncodedKeySpec.class))).thenAnswer(invocationOnMock -> {
                            throw exception.getDeclaredConstructor().newInstance();
                        });


                        rsaPrivateKeyFactory.create(Generator.randomBytes(Generator.randomInt(500, 1000)));


                    }
                });
            });
    }
}
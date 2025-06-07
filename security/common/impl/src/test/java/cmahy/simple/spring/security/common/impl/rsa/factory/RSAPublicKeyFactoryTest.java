package cmahy.simple.spring.security.common.impl.rsa.factory;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.common.api.rsa.exception.RSAPublicKeyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RSAPublicKeyFactoryTest {

    @InjectMocks
    private RSAPublicKeyFactory rsaPublicKeyFactory;

    @Mock
    private KeyFactory keyFactory;

    @Mock
    private RSAPublicKey publicKey;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            try (MockedStatic<KeyFactory> keyFactoryMocked = Mockito.mockStatic(KeyFactory.class)) {
                keyFactoryMocked.when(() -> KeyFactory.getInstance("RSA")).thenReturn(keyFactory);
                when(keyFactory.generatePublic(any(X509EncodedKeySpec.class))).thenReturn(publicKey);

                byte[] randomBytes = Generator.randomBytes(Generator.randomInt(500, 1000));


                RSAPublicKey actual = rsaPublicKeyFactory.create(randomBytes);


                assertThat(actual).isSameAs(publicKey);
            }
        });
    }

    @Test
    void create_givenBytesShouldNeverBeNull() {
        assertThrows(RSAPublicKeyException.class,() -> {


            rsaPublicKeyFactory.create(null);


        });
    }

    @Test
    void create_onAnyException_thenThrowRSAPublicKeyException() {
        Stream.of(
            NoSuchAlgorithmException.class,
            InvalidKeySpecException.class
        )
            .forEach(exception -> {
                assertThrows(RSAPublicKeyException.class, () -> {
                    try (MockedStatic<KeyFactory> keyFactoryMocked = Mockito.mockStatic(KeyFactory.class)) {
                        keyFactoryMocked.when(() -> KeyFactory.getInstance("RSA")).thenReturn(keyFactory);
                        when(keyFactory.generatePublic(any(X509EncodedKeySpec.class))).thenAnswer(invocationOnMock -> {
                            throw exception.getDeclaredConstructor().newInstance();
                        });


                        rsaPublicKeyFactory.create(Generator.randomBytes(Generator.randomInt(500, 1000)));


                    }
                });
            });
    }
}
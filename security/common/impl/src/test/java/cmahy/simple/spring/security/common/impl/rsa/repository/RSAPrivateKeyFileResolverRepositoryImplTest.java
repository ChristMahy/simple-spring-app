package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.common.api.rsa.exception.RSAPrivateKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPrivateKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PrivateKeyId;
import cmahy.simple.spring.security.common.impl.io.ResourceFactory;
import cmahy.simple.spring.security.common.impl.rsa.factory.RSAPrivateKeyFactory;
import cmahy.simple.spring.security.common.impl.rsa.service.NormalizePEMFileRSAKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RSAPrivateKeyFileResolverRepositoryImplTest {

    @Mock
    private ResourceFactory resourceFactory;

    @Mock
    private NormalizePEMFileRSAKey normalizePEMFileRsaKey;

    @Mock
    private RSAPrivateKeyFactory rsaPrivateKeyFactory;

    @Mock
    private RSAPrivateKeyConfigurationRepository rsaPrivateKeyConfigurationRepository;

    @InjectMocks
    private RSAPrivateKeyFileResolverRepositoryImpl rsaPrivateKeyFileResolverRepositoryImpl;

    @Test
    void findAll() {
        assertDoesNotThrow(() -> {

            PrivateKeyId idOne = mock(PrivateKeyId.class);
            String locationOne = Generator.generateAString();
            Resource resourceOne = mock(Resource.class);
            InputStream inputStreamOne = mock(InputStream.class);
            byte[] bytesOne = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPrivateKey rsaOne = mock(RSAPrivateKey.class);

            PrivateKeyId idTwo = mock(PrivateKeyId.class);
            String locationTwo = Generator.generateAString();
            Resource resourceTwo = mock(Resource.class);
            InputStream inputStreamTwo = mock(InputStream.class);
            byte[] bytesTwo = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPrivateKey rsaTwo = mock(RSAPrivateKey.class);

            PrivateKeyId idThree = mock(PrivateKeyId.class);
            String locationThree = Generator.generateAString();
            Resource resourceThree = mock(Resource.class);
            InputStream inputStreamThree = mock(InputStream.class);
            byte[] bytesThree = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPrivateKey rsaThree = mock(RSAPrivateKey.class);

            Map<PrivateKeyId, String> privateKeys = Map.of(
                idOne, locationOne,
                idTwo, locationTwo,
                idThree, locationThree
            );

            when(rsaPrivateKeyConfigurationRepository.allLocations()).thenReturn(privateKeys);

            when(resourceFactory.create(locationOne)).thenReturn(resourceOne);
            when(resourceOne.getInputStream()).thenReturn(inputStreamOne);
            when(inputStreamOne.readAllBytes()).thenReturn(bytesOne);
            when(normalizePEMFileRsaKey.execute(bytesOne)).thenReturn(bytesOne);
            when(rsaPrivateKeyFactory.create(bytesOne)).thenReturn(rsaOne);
            
            when(resourceFactory.create(locationTwo)).thenReturn(resourceTwo);
            when(resourceTwo.getInputStream()).thenReturn(inputStreamTwo);
            when(inputStreamTwo.readAllBytes()).thenReturn(bytesTwo);
            when(normalizePEMFileRsaKey.execute(bytesTwo)).thenReturn(bytesTwo);
            when(rsaPrivateKeyFactory.create(bytesTwo)).thenReturn(rsaTwo);
            
            when(resourceFactory.create(locationThree)).thenReturn(resourceThree);
            when(resourceThree.getInputStream()).thenReturn(inputStreamThree);
            when(inputStreamThree.readAllBytes()).thenReturn(bytesThree);
            when(normalizePEMFileRsaKey.execute(bytesThree)).thenReturn(bytesThree);
            when(rsaPrivateKeyFactory.create(bytesThree)).thenReturn(rsaThree);


            Map<PrivateKeyId, RSAPrivateKey> actual = rsaPrivateKeyFileResolverRepositoryImpl.findAll();


            assertThat(actual).isNotNull();
            assertThat(actual.size()).isEqualTo(3);

            assertThat(actual.get(idOne)).isSameAs(rsaOne);
            assertThat(actual.get(idTwo)).isSameAs(rsaTwo);
            assertThat(actual.get(idThree)).isSameAs(rsaThree);

        });
    }

    @Test
    void findAll_whenResourceThrowIOException_thenThrowRSAPublicKeyException() {
        assertThrows(RSAPrivateKeyException.class, () -> {

            PrivateKeyId idOne = mock(PrivateKeyId.class);
            String locationOne = Generator.generateAString();
            Resource resourceOne = mock(Resource.class);

            Map<PrivateKeyId, String> privateKeys = Map.of(
                idOne, locationOne
            );

            when(rsaPrivateKeyConfigurationRepository.allLocations()).thenReturn(privateKeys);

            when(resourceFactory.create(locationOne)).thenReturn(resourceOne);
            when(resourceOne.getInputStream()).thenThrow(IOException.class);


            rsaPrivateKeyFileResolverRepositoryImpl.findAll();

        });
    }

    @Test
    void findAll_whenNoneFound_thenReturnEmptyMap() {
        assertDoesNotThrow(() -> {

            when(rsaPrivateKeyConfigurationRepository.allLocations()).thenReturn(Collections.emptyMap());


            Map<PrivateKeyId, RSAPrivateKey> actual = rsaPrivateKeyFileResolverRepositoryImpl.findAll();


            assertThat(actual)
                .isNotNull()
                .isEmpty();

        });
    }

    @Test
    void findAll_whenLocationsIsNull_thenReturnEmptyMap() {
        assertDoesNotThrow(() -> {

            when(rsaPrivateKeyConfigurationRepository.allLocations()).thenReturn(null);


            Map<PrivateKeyId, RSAPrivateKey> actual = rsaPrivateKeyFileResolverRepositoryImpl.findAll();


            assertThat(actual)
                .isNotNull()
                .isEmpty();

        });
    }

    @Test
    void findById() {
        assertDoesNotThrow(() -> {

            PrivateKeyId id = mock(PrivateKeyId.class);
            String location = Generator.generateAString();
            Resource resource = mock(Resource.class);
            InputStream inputStream = mock(InputStream.class);
            byte[] bytes = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPrivateKey rsa = mock(RSAPrivateKey.class);

            when(rsaPrivateKeyConfigurationRepository.getLocation(id)).thenReturn(Optional.of(location));

            when(resourceFactory.create(location)).thenReturn(resource);
            when(resource.getInputStream()).thenReturn(inputStream);
            when(inputStream.readAllBytes()).thenReturn(bytes);
            when(normalizePEMFileRsaKey.execute(bytes)).thenReturn(bytes);
            when(rsaPrivateKeyFactory.create(bytes)).thenReturn(rsa);


            RSAPrivateKey actual = rsaPrivateKeyFileResolverRepositoryImpl.findById(id);


            assertThat(actual)
                .isNotNull()
                .isSameAs(rsa);

        });
    }

    @Test
    void findById_whenLocationNotFound_thenThrowRSAPrivateKeyException() {
        assertThrows(RSAPrivateKeyException.class, () -> {

            PrivateKeyId privateKeyId = mock(PrivateKeyId.class);

            when(rsaPrivateKeyConfigurationRepository.getLocation(privateKeyId)).thenReturn(Optional.empty());


            rsaPrivateKeyFileResolverRepositoryImpl.findById(privateKeyId);


        });
    }

    @Test
    void findById_whenResourceThrowIOException_thenThrowRSAPublicKeyException() {
        assertThrows(RSAPrivateKeyException.class, () -> {

            PrivateKeyId id = mock(PrivateKeyId.class);
            String location = Generator.generateAString();
            Resource resource = mock(Resource.class);

            when(rsaPrivateKeyConfigurationRepository.getLocation(id)).thenReturn(Optional.of(location));

            when(resourceFactory.create(location)).thenReturn(resource);
            when(resource.getInputStream()).thenThrow(IOException.class);


            rsaPrivateKeyFileResolverRepositoryImpl.findById(id);


        });
    }

}
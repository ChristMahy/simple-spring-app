package cmahy.simple.spring.security.common.impl.rsa.repository;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.common.api.rsa.exception.RSAPublicKeyException;
import cmahy.simple.spring.security.common.api.rsa.repository.RSAPublicKeyConfigurationRepository;
import cmahy.simple.spring.security.common.api.rsa.vo.id.PublicKeyId;
import cmahy.simple.spring.security.common.impl.io.ResourceFactory;
import cmahy.simple.spring.security.common.impl.rsa.factory.RSAPublicKeyFactory;
import cmahy.simple.spring.security.common.impl.rsa.service.NormalizePEMFileRSAKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RSAPublicKeyFileResolverRepositoryImplTest {

    @Mock
    private ResourceFactory resourceFactory;
    
    @Mock
    private NormalizePEMFileRSAKey normalizePEMFileRsaKey;
    
    @Mock
    private RSAPublicKeyFactory rsaPublicKeyFactory;
    
    @Mock
    private RSAPublicKeyConfigurationRepository rsaPublicKeyConfigurationRepository;

    @InjectMocks
    private RSAPublicKeyFileResolverRepositoryImpl rsaPublicKeyFileResolverRepositoryImpl;

    @Test
    void findAll() {
        assertDoesNotThrow(() -> {
            PublicKeyId idOne = mock(PublicKeyId.class);
            String locationOne = Generator.generateAString();
            Resource resourceOne = mock(Resource.class);
            InputStream inputStreamOne = mock(InputStream.class);
            byte[] bytesOne = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPublicKey rsaOne = mock(RSAPublicKey.class);

            PublicKeyId idTwo = mock(PublicKeyId.class);
            String locationTwo = Generator.generateAString();
            Resource resourceTwo = mock(Resource.class);
            InputStream inputStreamTwo = mock(InputStream.class);
            byte[] bytesTwo = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPublicKey rsaTwo = mock(RSAPublicKey.class);

            PublicKeyId idThree = mock(PublicKeyId.class);
            String locationThree = Generator.generateAString();
            Resource resourceThree = mock(Resource.class);
            InputStream inputStreamThree = mock(InputStream.class);
            byte[] bytesThree = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPublicKey rsaThree = mock(RSAPublicKey.class);

            Map<PublicKeyId, String> publicKeys = Map.of(
                idOne, locationOne,
                idTwo, locationTwo,
                idThree, locationThree
            );

            when(rsaPublicKeyConfigurationRepository.allLocations()).thenReturn(publicKeys);

            when(resourceFactory.create(locationOne)).thenReturn(resourceOne);
            when(resourceOne.getInputStream()).thenReturn(inputStreamOne);
            when(inputStreamOne.readAllBytes()).thenReturn(bytesOne);
            when(normalizePEMFileRsaKey.execute(bytesOne)).thenReturn(bytesOne);
            when(rsaPublicKeyFactory.create(bytesOne)).thenReturn(rsaOne);
            
            when(resourceFactory.create(locationTwo)).thenReturn(resourceTwo);
            when(resourceTwo.getInputStream()).thenReturn(inputStreamTwo);
            when(inputStreamTwo.readAllBytes()).thenReturn(bytesTwo);
            when(normalizePEMFileRsaKey.execute(bytesTwo)).thenReturn(bytesTwo);
            when(rsaPublicKeyFactory.create(bytesTwo)).thenReturn(rsaTwo);
            
            when(resourceFactory.create(locationThree)).thenReturn(resourceThree);
            when(resourceThree.getInputStream()).thenReturn(inputStreamThree);
            when(inputStreamThree.readAllBytes()).thenReturn(bytesThree);
            when(normalizePEMFileRsaKey.execute(bytesThree)).thenReturn(bytesThree);
            when(rsaPublicKeyFactory.create(bytesThree)).thenReturn(rsaThree);


            Map<PublicKeyId, RSAPublicKey> actual = rsaPublicKeyFileResolverRepositoryImpl.findAll();


            assertThat(actual).isNotNull();
            assertThat(actual.size()).isEqualTo(3);

            assertThat(actual.get(idOne)).isSameAs(rsaOne);
            assertThat(actual.get(idTwo)).isSameAs(rsaTwo);
            assertThat(actual.get(idThree)).isSameAs(rsaThree);
        });
    }

    @Test
    void findAll_whenResourceThrowIOException_thenThrowRSAPublicKeyException() {
        assertThrows(RSAPublicKeyException.class, () -> {

            PublicKeyId idOne = mock(PublicKeyId.class);
            String locationOne = Generator.generateAString();
            Resource resourceOne = mock(Resource.class);

            Map<PublicKeyId, String> publicKeys = Map.of(
                idOne, locationOne
            );

            when(rsaPublicKeyConfigurationRepository.allLocations()).thenReturn(publicKeys);

            when(resourceFactory.create(locationOne)).thenReturn(resourceOne);
            when(resourceOne.getInputStream()).thenThrow(IOException.class);


            rsaPublicKeyFileResolverRepositoryImpl.findAll();

        });
    }

    @Test
    void findAll_whenNoneFound_thenReturnEmptyMap() {
        assertDoesNotThrow(() -> {

            when(rsaPublicKeyConfigurationRepository.allLocations()).thenReturn(Collections.emptyMap());


            Map<PublicKeyId, RSAPublicKey> actual = rsaPublicKeyFileResolverRepositoryImpl.findAll();


            assertThat(actual)
                .isNotNull()
                .isEmpty();

        });
    }

    @Test
    void findAll_whenLocationsIsNull_thenReturnEmptyMap() {
        assertDoesNotThrow(() -> {

            when(rsaPublicKeyConfigurationRepository.allLocations()).thenReturn(null);


            Map<PublicKeyId, RSAPublicKey> actual = rsaPublicKeyFileResolverRepositoryImpl.findAll();


            assertThat(actual)
                .isNotNull()
                .isEmpty();

        });
    }

    @Test
    void findById() {
        assertDoesNotThrow(() -> {

            PublicKeyId id = mock(PublicKeyId.class);
            String location = Generator.generateAString();
            Resource resource = mock(Resource.class);
            InputStream inputStream = mock(InputStream.class);
            byte[] bytes = Generator.randomBytes(Generator.randomInt(200, 1000));
            RSAPublicKey rsa = mock(RSAPublicKey.class);

            when(rsaPublicKeyConfigurationRepository.getLocation(id)).thenReturn(Optional.of(location));

            when(resourceFactory.create(location)).thenReturn(resource);
            when(resource.getInputStream()).thenReturn(inputStream);
            when(inputStream.readAllBytes()).thenReturn(bytes);
            when(normalizePEMFileRsaKey.execute(bytes)).thenReturn(bytes);
            when(rsaPublicKeyFactory.create(bytes)).thenReturn(rsa);


            RSAPublicKey actual = rsaPublicKeyFileResolverRepositoryImpl.findById(id);


            assertThat(actual)
                .isNotNull()
                .isSameAs(rsa);

        });
    }

    @Test
    void findById_whenLocationNotFound_thenThrowRSAPrivateKeyException() {
        assertThrows(RSAPublicKeyException.class, () -> {

            PublicKeyId publicKeyId = mock(PublicKeyId.class);

            when(rsaPublicKeyConfigurationRepository.getLocation(publicKeyId)).thenReturn(Optional.empty());


            rsaPublicKeyFileResolverRepositoryImpl.findById(publicKeyId);


        });
    }

    @Test
    void findById_whenResourceThrowIOException_thenThrowRSAPublicKeyException() {
        assertThrows(RSAPublicKeyException.class, () -> {

            PublicKeyId id = mock(PublicKeyId.class);
            String location = Generator.generateAString();
            Resource resource = mock(Resource.class);

            when(rsaPublicKeyConfigurationRepository.getLocation(id)).thenReturn(Optional.of(location));

            when(resourceFactory.create(location)).thenReturn(resource);
            when(resource.getInputStream()).thenThrow(IOException.class);


            rsaPublicKeyFileResolverRepositoryImpl.findById(id);


        });
    }
}
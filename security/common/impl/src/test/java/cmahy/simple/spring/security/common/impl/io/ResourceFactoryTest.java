package cmahy.simple.spring.security.common.impl.io;

import cmahy.simple.spring.common.helper.Generator;
import cmahy.simple.spring.security.common.impl.rsa.exception.ResourceNotFoundException;
import cmahy.simple.spring.security.common.impl.rsa.exception.UnreachableResourceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResourceFactoryTest {

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private ResourceFactory resourceFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            String location = "file:/" +
                Generator.generateAStringWithoutSpecialChars() + "/" +
                Generator.generateAStringWithoutSpecialChars() + "/" +
                Generator.generateAStringWithoutSpecialChars() + ".smt";
            Resource resource = mock(Resource.class);

            when(resource.exists()).thenReturn(Boolean.TRUE);

            when(resourceLoader.getResource(location)).thenReturn(resource);


            Resource actual = resourceFactory.create(location);


            assertThat(actual)
                .isNotNull()
                .isSameAs(resource);
        });
    }

    @Test
    void create_whenUnreachableResource_thenThrowException() {
        assertThrows(UnreachableResourceException.class, () -> {
            String missingURLProtocolLocation = "/" +
                Generator.generateAStringWithoutSpecialChars() + "/" +
                Generator.generateAStringWithoutSpecialChars() + "/" +
                Generator.generateAStringWithoutSpecialChars() + ".smt";


            resourceFactory.create(missingURLProtocolLocation);


        });
    }

    @Test
    void create_whenResourceDoesNotExist_thenThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            String location = "file:/" +
                Generator.generateAStringWithoutSpecialChars() + "/" +
                Generator.generateAStringWithoutSpecialChars() + "/" +
                Generator.generateAStringWithoutSpecialChars() + ".smt";
            Resource resource = mock(Resource.class);

            when(resource.exists()).thenReturn(Boolean.FALSE);

            when(resourceLoader.getResource(location)).thenReturn(resource);


            resourceFactory.create(location);


        });
    }
}
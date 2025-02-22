package cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.factory;

import cmahy.simple.spring.webapp.resource.impl.application.stream.service.zip.ZipProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ZipEntryProxyFactoryTest {

    @InjectMocks
    private ZipEntryProxyFactory zipEntryProxyFactory;

    @Test
    void create() {
        assertDoesNotThrow(() -> {
            var zipProxy = mock(ZipProxy.class);

            var zipEntryProxy = zipEntryProxyFactory.create(zipProxy);

            assertThat(zipEntryProxy).isNotNull();
        });
    }
}
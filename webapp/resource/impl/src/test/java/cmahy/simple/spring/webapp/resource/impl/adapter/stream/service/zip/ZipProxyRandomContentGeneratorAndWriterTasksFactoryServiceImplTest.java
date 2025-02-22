//package cmahy.simple.spring.webapp.resource.impl.adapter.stream.service.zip;
//
//import cmahy.simple.spring.common.helper.Generator;
//import service.application.cmahy.simple.spring.webapp.resource.impl.StringGeneratorService;
//import zip.service.stream.application.cmahy.simple.spring.webapp.resource.impl.ZipEntryProxy;
//import executor.zip.service.stream.application.cmahy.simple.spring.webapp.resource.impl.ZipSingleEntryTaskExecutor;
//import option.vo.stream.application.cmahy.simple.spring.webapp.resource.impl.GeneratorQueryOption;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//import java.util.Random;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ZipProxyRandomContentGeneratorAndWriterTasksFactoryServiceImplTest {
//
//    @Mock
//    private StringGeneratorService stringGeneratorService;
//
//    @Mock
//    private Random randomizer;
//
//    @InjectMocks
//    private ZipRandomContentGeneratorAndWriterTasksFactoryImpl zipRandomContentGeneratorAndWriterTasksFactoryServiceImpl;
//
//    @Test
//    void execute() {
//        assertDoesNotThrow(() -> {
//            var elementsSize = Generator.randomInt(50, 1000);
//            var stringSize = Generator.randomInt(20, 100);
//            var options = new GeneratorQueryOption(
//                Boolean.FALSE,
//                Optional.of(stringSize),
//                Optional.of(elementsSize)
//            );
//            var zipEntry = mock(ZipEntryProxy.class);
//            var generatedString = Generator.generateAString();
//
//            when(randomizer.nextInt(0, elementsSize)).thenCallRealMethod();
//            when(stringGeneratorService.execute(stringSize)).thenReturn(generatedString);
//
//            var actual = zipRandomContentGeneratorAndWriterTasksFactoryServiceImpl.execute(options);
//
//            assertThat(actual).hasSize(elementsSize);
//
//            for (ZipSingleEntryTaskExecutor task : actual) {
//                task.execute(zipEntry);
//            }
//
//            verify(stringGeneratorService, times(elementsSize)).execute(stringSize);
//            verify(zipEntry, times(elementsSize)).write(any(byte[].class));
//        });
//    }
//
//    @Test
//    void execute_whenFailureAskedAtRandomPosition_thenGenerateElementUntilFailurePointBreakIsNotReachedThenThrowException() {
//        var elementsSize = Generator.randomInt(50, 1000);
//        var stringSize = Generator.randomInt(20, 100);
//        var failureAt = Generator.randomInt(0, elementsSize);
//
//        assertThrows(RuntimeException.class, () -> {
//            var options = new GeneratorQueryOption(
//                Boolean.TRUE,
//                Optional.of(stringSize),
//                Optional.of(elementsSize)
//            );
//            var zipEntry = mock(ZipEntryProxy.class);
//            var generatedString = Generator.generateAString();
//
//            when(randomizer.nextInt(0, elementsSize)).thenReturn(failureAt);
//            when(stringGeneratorService.execute(stringSize)).thenReturn(generatedString);
//
//            var actual = zipRandomContentGeneratorAndWriterTasksFactoryServiceImpl.execute(options);
//
//            assertThat(actual).hasSize(elementsSize);
//
//            for (ZipSingleEntryTaskExecutor task : actual) {
//                task.execute(zipEntry);
//            }
//        });
//
//        verify(stringGeneratorService, times(failureAt)).execute(stringSize);
//    }
//}
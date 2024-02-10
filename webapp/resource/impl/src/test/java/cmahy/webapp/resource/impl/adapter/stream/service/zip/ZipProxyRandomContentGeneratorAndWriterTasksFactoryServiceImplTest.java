//package cmahy.webapp.resource.impl.adapter.stream.service.zip;
//
//import cmahy.common.helper.Generator;
//import cmahy.webapp.resource.impl.application.service.StringGeneratorService;
//import cmahy.webapp.resource.impl.application.stream.service.zip.ZipEntryProxy;
//import cmahy.webapp.resource.impl.application.stream.service.zip.executor.ZipSingleEntryTaskExecutor;
//import cmahy.webapp.resource.impl.application.stream.vo.option.GeneratorQueryOption;
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
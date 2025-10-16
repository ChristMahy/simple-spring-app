package cmahy.plugins.architecture.rule;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

import java.util.Collection;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

public final class MainCodingRule {

    private MainCodingRule() {}

    public static final Collection<ImportOption> IMPORTS_OPTIONS = Set.of(
        ImportOption.Predefined.DO_NOT_INCLUDE_TESTS,
        ImportOption.Predefined.DO_NOT_INCLUDE_JARS,
        ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES
    );

    public static final ArchRule AVOID_IO_STANDARD_STREAM_USE = noClasses()
        .should(ACCESS_STANDARD_STREAMS);

    public static final ArchRule AVOID_JAVA_UTIL_LOGGING_USE = noClasses()
        .should(USE_JAVA_UTIL_LOGGING);

    public static final ArchRule LOGGER_AS_PRIVATE_STATIC_FINAL = fields()
        .that().haveRawType("org.slf4j.Logger")
        .should().bePrivate()
        .andShould().beStatic()
        .andShould().beFinal()
        .allowEmptyShould(true);

    public static final ArchRule AVOID_JODA_TIME_USE = NO_CLASSES_SHOULD_USE_JODATIME;

}

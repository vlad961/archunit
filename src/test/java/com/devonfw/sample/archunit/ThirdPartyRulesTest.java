package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import javax.transaction.Transactional;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ThirdPartyRulesTest {
    @ArchTest
    static final ArchRule no_classes_should_access_springframework_transactional_annotation =
            noClasses()
            .should().accessClassesThat().haveFullyQualifiedName("org.springframework.transaction.annotation.Transactional")
            .because("Use JEE standard (javax.transaction.Transactional from javax.transaction:javax.transaction-api:1.2+).");
    @ArchTest
    static final ArchRule no_api_scoped_classes_should_access_transactional_annotation =
            noClasses()
            .that().resideInAPackage("..api..")
            .should().beAnnotatedWith(Transactional.class)
            .allowEmptyShould(true);
}

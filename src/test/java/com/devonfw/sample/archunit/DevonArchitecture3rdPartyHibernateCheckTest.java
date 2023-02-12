package com.devonfw.sample.archunit;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tngtech.archunit.base.DescribedPredicate;

@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class DevonArchitecture3rdPartyHibernateCheckTest {

    private static final Set<String> DISCOURAGED_HIBERNATE_ANNOTATIONS = new HashSet<>(
        Arrays.asList("OrderBy", "Entity", "AccessType", "ForeignKey", "Cascade", "Index", "IndexColumn"));
  
    private static final String ORG_HIBERNATE_ENVERS = "org.hibernate.envers";
  
    private static final String ORG_HIBERNATE_VALIDATOR = "org.hibernate.validator";
  
    private static final String ORG_HIBERNATE_ANNOTATIONS = "org.hibernate.annotations";

    static DescribedPredicate<JavaClass> dependOnHibernate = new DescribedPredicate<JavaClass>("depend on hibernate") {
        @Override
        public boolean test(JavaClass input) {
            for(Dependency access: input.getDirectDependenciesFromSelf()) {
                String targetPackageName = access.getTargetClass().getPackageName();
                if(targetPackageName.startsWith("org.hibernate") && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
                    return true;
                }
            }
            return false;
        }
    };
    
    static ArchCondition<JavaClass> isUsingHibernateOutsideOfDataaccessLayer = new ArchCondition<JavaClass> ("depend on hibernate packages other than org.hibernate.validator outside of dataaccess layer.") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            for(Dependency access: item.getDirectDependenciesFromSelf()) {
                String targetPackageName = access.getTargetClass().getPackageName();
                String targetPackageFullName = access.getTargetClass().getFullName();
                String targetClassDescription = access.getDescription();
                
                if(!item.getPackageName().contains("dataaccess") && targetPackageName.startsWith("org.hibernate") && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
                    String message = String.format("Hibernate (%s) should only be used in dataaccess layer. Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
            }
        }
    };

    static ArchCondition<JavaClass> isUsingProprietaryHibernateAnnotation = new ArchCondition<JavaClass> ("use discouraged hibernate annotations:" + DISCOURAGED_HIBERNATE_ANNOTATIONS+ " .") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            for(Dependency access: item.getDirectDependenciesFromSelf()) {
                String targetPackageName = access.getTargetClass().getPackageName();
                String targetPackageFullName = access.getTargetClass().getFullName();
                String targetClassDescription = access.getDescription();
                String targetSimpleName = access.getTargetClass().getSimpleName();
                if(targetPackageName.equals(ORG_HIBERNATE_ANNOTATIONS) && DISCOURAGED_HIBERNATE_ANNOTATIONS.contains(targetSimpleName)) {
                    String message = String.format("Standard JPA annotations should be used instead of this proprietary hibernate annotation (%s). Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }  
            }
        }
    };

    static ArchCondition<JavaClass> isNotImplementingHibernateEnversInImplScope = new ArchCondition<JavaClass> ("implement hibernate.evnvers outside of implementation scope.") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            for(Dependency access: item.getDirectDependenciesFromSelf()) {
                String targetPackageName = access.getTargetClass().getPackageName();
                String targetPackageFullName = access.getTargetClass().getFullName();
                String targetClassDescription = access.getDescription();
                String targetSimpleName = access.getTargetClass().getSimpleName();
                if(targetPackageName.startsWith(ORG_HIBERNATE_ENVERS) && !item.getPackageName().contains("..impl..")
                    && (!targetPackageFullName.equals(ORG_HIBERNATE_ENVERS) || targetSimpleName.startsWith("Default")
                    || targetSimpleName.contains("Listener") || targetSimpleName.contains("Reader"))) 
                        {
                            String message = String.format("Hibernate envers implementation (%s) should only be used in impl scope of dataaccess layer. Violated in (%s)", targetPackageFullName, targetClassDescription);
                            events.add(new SimpleConditionEvent(item, true, message));
                        }  
            }
        }
    };
     
    static ArchCondition<JavaClass> isImplementingHibernateEnversInternalsDirectly = new ArchCondition<JavaClass> ("use hibernate envers internal directly.") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            for(Dependency access: item.getDirectDependenciesFromSelf()) {
                String targetPackageName = access.getTargetClass().getPackageName();
                String targetPackageFullName = access.getTargetClass().getFullName();
                String targetClassDescription = access.getDescription();
                if(targetPackageName.startsWith(ORG_HIBERNATE_ENVERS) && targetPackageName.contains("internal")) {
                    String message = String.format("Hibernate envers internals (%s) should never be used directly. Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
            }
        }
    };

    static ArchCondition<JavaClass> isUsingHibernateOutsideOfImplScope = new ArchCondition<JavaClass> ("use hibernate internals outside of the impl scope of the dataaccess layer.") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            for(Dependency access: item.getDirectDependenciesFromSelf()) {
                String targetPackageName = access.getTargetClass().getPackageName();
                String targetPackageFullName = access.getTargetClass().getFullName();
                String targetClassDescription = access.getDescription();
                if(!item.getPackageName().contains("impl") && targetPackageName.startsWith("org.hibernate") && !targetPackageName.startsWith(ORG_HIBERNATE_VALIDATOR)) {
                    String message = String.format("Hibernate internals (%s) should only be used in impl scope of dataaccess layer. Violated in (%s)", targetPackageFullName, targetClassDescription);
                    events.add(new SimpleConditionEvent(item, true, message));
                }
            }
        }
    };
    @ArchTest
    static final ArchRule rule2 = 
        noClasses()
        .that(dependOnHibernate)
        .should(isUsingHibernateOutsideOfImplScope)
        .allowEmptyShould(true);



    ArchCondition<JavaClass> onlyHaveDependenciesTo(final Class<?> allowedType) {
        return new ArchCondition<JavaClass>("only have dependencies to " + allowedType.getName()) {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                javaClass.getDirectDependenciesFromSelf().stream()
                        .filter(d -> d.getTargetClass().getSimpleName().equals(allowedType.getSimpleName()))
                        .filter(d -> !d.getTargetClass().isEquivalentTo(allowedType))
                        .forEach(d -> events.add(SimpleConditionEvent.violated(d, d.getDescription())));
            }
        };
    }
}

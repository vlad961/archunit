package com.devonfw.sample.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;

/**
 * verifying that the logic layer of a component may not depend on the
 * dataaccess layer of another component.
 */
@AnalyzeClasses(packages = "com.devonfw.sample.archunit", importOptions = ImportOption.DoNotIncludeTests.class)
public class ComponentRuleC5LayerLogic2Dataaccess4ComponentTest {

    static final String PROJECT_NAME = "com.devonfw.sample.archunit";
    static final String DEFAULT_COMPONENT_SIMPLE_NAME = "general";
    static final String DEFAULT_COMPONENT_FULL_NAME = PROJECT_NAME + "." + DEFAULT_COMPONENT_SIMPLE_NAME;

    static ArchCondition<JavaClass> haveNonCompliantComponentDependencies = new ArchCondition<JavaClass> ("have dependencies, towards another components dataaccess layer (Rule-C5).") {
        @Override
        public void check(JavaClass sourceClass, ConditionEvents events) {
            String sourceClassName = sourceClass.getFullName();
            String sourceClassLayer = getClassLayer(sourceClass);
            String sourceClassComponent = getComponentNameOfClass(sourceClass);

            // All project components logic layer except the default component.
            if(sourceClassLayer.equals("logic") && !sourceClassComponent.equals("")  && !sourceClassComponent.equals(DEFAULT_COMPONENT_SIMPLE_NAME)){
                for (Dependency dependency : sourceClass.getDirectDependenciesFromSelf()) {
                    JavaClass targetClass = dependency.getTargetClass();
                    String targetClassName = targetClass.getFullName();
                    String targetClassComponent = getComponentNameOfClass(targetClass);
                    String targetClassLayer = getClassLayer(targetClass);
                    boolean dependencyAllowed = isAllowedDependency(sourceClass, targetClass);

                    // WARNING: Dependency of a components logic layer towards another components layer other than logic wont be registered as a violation. 
                    // (Other rules cover these violations.)
                    if(targetClassName.startsWith(PROJECT_NAME) && targetClassLayer.equals("logic") && !dependencyAllowed) {
                        String message = String.format("Code from logic layer of a component shall not depend on dataaccess layer of a different component. ('%s.%s' is dependend on '%s.%s'. Dependency to (%s). Violated in: (%s)", sourceClassComponent, sourceClassLayer, targetClassComponent, targetClassLayer, targetClass.getDescription(), sourceClassName);
                        events.add(new SimpleConditionEvent(sourceClass, true, message));
                    }
                }
            }
        }
    };
    
    @ArchTest
    static final ArchRule no_dependencies_from_a_components_logic_layer_to_anothers_component_dataaccess_layer = 
        noClasses()
        .should(haveNonCompliantComponentDependencies)
        .allowEmptyShould(true);

    /**
     * Dependency of a components logic layer towards the same components dataaccess or common layer is allowed.
     */
    private static boolean isAllowedDependency(JavaClass sourceClass, JavaClass targetClass) {
        boolean isAllowed = false;
        String targetClassName = targetClass.getFullName();

        // Components logic layer can depend on their own lower layers: dataaccess and common.
        if(classesAreInSameComponent(sourceClass, targetClass) && (targetClassName.contains("dataaccess") || targetClassName.contains("common"))) {
            isAllowed = true;
        }
        // Components may always depend on the default business component.
        if(targetClassName.startsWith(DEFAULT_COMPONENT_FULL_NAME) && (targetClassName.contains("dataaccess") || targetClassName.contains("common"))) {
            isAllowed = true;
        }
        return isAllowed;
    }

    /**
     * Returns the name of the layer of a given class, if the class does lie in a projects component.
     * Otherwise returns a blank string "".
     */
    private static String getClassLayer(JavaClass clazz) {
        String classLayerName = "";
        String className = clazz.getFullName();
        if(className.startsWith(PROJECT_NAME)) {
            String classPackageName = clazz.getPackageName();
            String classComponentAndLayerName = classPackageName.substring(PROJECT_NAME.length() + 1, classPackageName.length());
            int beginOfLayerName = classComponentAndLayerName.indexOf(".") + 1;
            int endOfLayerName = classComponentAndLayerName.length();
            classLayerName = classComponentAndLayerName.substring(beginOfLayerName, endOfLayerName);
        }
        return classLayerName;
    }

    /**
     * Returns the name of the component of a given class, if the class does lie in a projects component.
     * Otherwise returns a blank string "".
     */
    private static String getComponentNameOfClass(JavaClass clazz) {
        String classComponentName = "";
        String className = clazz.getFullName();
        if(className.startsWith(PROJECT_NAME)) {
            String classPackageName = clazz.getPackageName();
            String classComponentAndLayerName = classPackageName.substring(PROJECT_NAME.length() + 1, classPackageName.length());
            int endOfComponentName = classComponentAndLayerName.indexOf(".");
            classComponentName = classComponentAndLayerName.substring(0, endOfComponentName);
        }
        return classComponentName;
    }

    /**
     * Returns true, if both given classes lie in the same projects component.
     * In case at least one of the given classes does not lie in any of the project components false will be returned.
     * @param sourceClass JavaClass A to check against another JavaClass B.
     * @param targetClass JavaClass B to check against another JavaClass A.
     */
    private static boolean classesAreInSameComponent(JavaClass sourceClass, JavaClass targetClass) {
        String sourceComponent = getComponentNameOfClass(sourceClass);
        String targetComponent = getComponentNameOfClass(targetClass);
        if( targetComponent == "" || sourceComponent =="")
        {
            return false;
        }
        return sourceComponent.equals(targetComponent) ? true : false;
    }

}

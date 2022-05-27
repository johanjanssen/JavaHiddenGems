package com.examples;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures.LayeredArchitecture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArchUnitTest {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("com.example");

    // Each class name in a controller package with a @RestController annotation should end with 'Controller'
    @Test
    void controllerClassNameShouldEndWithController() {
        ArchRule rule = classes()
                .that().resideInAPackage("..controller..")
                .and().areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller");

        AssertionError assertionError = Assertions.assertThrows(AssertionError.class, () -> {
            rule.check(importedClasses);
        });

        String expectedMessage = """
            Architecture Violation [Priority: MEDIUM] - Rule 'classes that reside in a package '..controller..' and are annotated with @RestController should have simple name ending with 'Controller'' was violated (1 times):
            simple name of com.example.payment.controller.WronglyNamed does not end with 'Controller' in (WronglyNamed.java:0)""";

        assertEquals(expectedMessage, assertionError.getMessage().replaceAll("\\r\\n", "\n"));
    }

    // Classes in the payment package should only use classes in the common or java packages.
    @Test
    void paymentFeatureShouldNotDependOnOtherFeatures() {
        ArchRule rule = classes()
                .that().resideInAPackage("..payment..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("com.example.common..", "java..", "org.springframework..");

        AssertionError assertionError = Assertions.assertThrows(AssertionError.class, () -> {
            rule.check(importedClasses);
        });

        String expectedMessage = """
                Architecture Violation [Priority: MEDIUM] - Rule 'classes that reside in a package '..payment..' should only depend on classes that reside in any package ['com.example.common..', 'java..', 'org.springframework..']' was violated (3 times):
                Constructor <com.example.payment.controller.PaymentController.<init>(com.example.reservation.service.ReservationService)> has parameter of type <com.example.reservation.service.ReservationService> in (PaymentController.java:0)
                Field <com.example.payment.controller.PaymentController.reservationService> has type <com.example.reservation.service.ReservationService> in (PaymentController.java:0)
                Method <com.example.payment.controller.PaymentController.paymentFunction()> calls method <com.example.reservation.service.ReservationService.reservationServiceFunction()> in (PaymentController.java:18)""";

        assertEquals(expectedMessage, assertionError.getMessage().replaceAll("\\r\\n", "\n"));
    }

    // Classes in the common package shouldn't use classes outside of the package
    @Test
    void commonCodeShouldNotDependOnOtherFeatures() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("com.example.common..")
                .should().dependOnClassesThat()
                .resideOutsideOfPackages("com.example.common..", "java..", "org.springframework..");

        AssertionError assertionError = Assertions.assertThrows(AssertionError.class, () -> {
            rule.check(importedClasses);
        });

        String expectedMessage = """
                Architecture Violation [Priority: MEDIUM] - Rule 'no classes that reside in a package 'com.example.common..' should depend on classes that reside outside of packages ['com.example.common..', 'java..', 'org.springframework..']' was violated (3 times):
                Constructor <com.example.common.service.CommonService.<init>(com.example.reservation.service.ReservationService)> has parameter of type <com.example.reservation.service.ReservationService> in (CommonService.java:0)
                Field <com.example.common.service.CommonService.reservationService> has type <com.example.reservation.service.ReservationService> in (CommonService.java:0)
                Method <com.example.common.service.CommonService.commonFunction()> calls method <com.example.reservation.service.ReservationService.reservationServiceFunction()> in (CommonService.java:15)""";

        assertEquals(expectedMessage, assertionError.getMessage().replaceAll("\\r\\n", "\n"));
    }

    // Classes in the controller package cannot be used by any later.
    // Classes in the service package can be used by classes in the controller layer
    @Test
    void controllerShouldOnlyUseService() {
        LayeredArchitecture arch = layeredArchitecture()
                // Define layers
                .layer("Controller").definedBy("..controller..")
                .layer("Service").definedBy("..service..")
                // Add constraints
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller");

        AssertionError assertionError = Assertions.assertThrows(AssertionError.class, () -> {
            arch.check(importedClasses);
        });

        String expectedMessage = """
            Architecture Violation [Priority: MEDIUM] - Rule 'Layered architecture consisting of
            layer 'Controller' ('..controller..')
            layer 'Service' ('..service..')
            where layer 'Controller' may not be accessed by any layer
            where layer 'Service' may only be accessed by layers ['Controller']' was violated (3 times):
            Constructor <com.example.reservation.service.ReservationService.<init>(com.example.reservation.controller.ReservationController)> has parameter of type <com.example.reservation.controller.ReservationController> in (ReservationService.java:0)
            Field <com.example.reservation.service.ReservationService.reservationController> has type <com.example.reservation.controller.ReservationController> in (ReservationService.java:0)
            Method <com.example.reservation.service.ReservationService.reservationServiceFunction()> calls method <com.example.reservation.controller.ReservationController.reservationControllerFunction()> in (ReservationService.java:16)""";

        assertEquals(expectedMessage, assertionError.getMessage().replaceAll("\\r\\n", "\n"));
    }
}

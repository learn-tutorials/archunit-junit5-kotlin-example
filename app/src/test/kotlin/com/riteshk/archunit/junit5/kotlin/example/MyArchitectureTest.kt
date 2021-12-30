package com.riteshk.archunit.junit5.kotlin.example;

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.domain.JavaConstructor
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchCondition
import com.tngtech.archunit.lang.ConditionEvents
import com.tngtech.archunit.lang.SimpleConditionEvent
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.constructors
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses


@AnalyzeClasses(packagesOf = [MyArchitectureTest::class])
class MyArchitectureTest {
    private val BASE_PACKAGE = MyArchitectureTest::class.java.`package`.name
    private val MODEL_PACKAGE = "$BASE_PACKAGE.model"
    private val SERVICE_PACKAGE = "$BASE_PACKAGE.service"
    private val UTIL_PACKAGE = "$BASE_PACKAGE.util"
    private val CONTROLLER_PACKAGE = "$BASE_PACKAGE.controller"

    @ArchTest
    fun `the model does not have outgoing dependencies`(importedClasses: JavaClasses) {
        val rule = noClasses()
            .that()
            .resideInAPackage("$MODEL_PACKAGE..")
            .should()
            .accessClassesThat()
            .resideInAnyPackage("$SERVICE_PACKAGE..", "$CONTROLLER_PACKAGE..")
        rule.check(importedClasses)
    }

    @ArchTest
    fun `no classes should reside outside base package`(importedClasses: JavaClasses) {
        val rule = noClasses()
            .that()
            .resideInAPackage("$SERVICE_PACKAGE..")
            .should().haveSimpleNameNotEndingWith("Service")
        rule.check(importedClasses)
    }

    @ArchTest
    fun `classes inside service package should have name ending with service`(importedClasses: JavaClasses) {
        val rule = noClasses().should().resideOutsideOfPackage("${BASE_PACKAGE}..")
        rule.check(importedClasses)
    }

    @ArchTest
    fun `classes should not have any parameter`(importedClasses: JavaClasses) {
        val rule = constructors().that().areDeclaredInClassesThat().resideInAPackage("$UTIL_PACKAGE..")
            .should(haveNoParameters())
        rule.check(importedClasses)
    }

    private fun haveNoParameters() = object : ArchCondition<JavaConstructor>("no arg constructors") {
        override fun check(constructor: JavaConstructor, events: ConditionEvents) {
            val message = "Constructors should not have parameters"
            events.add(SimpleConditionEvent(constructor, constructor.parameters.isEmpty(), message))
        }
    }
}
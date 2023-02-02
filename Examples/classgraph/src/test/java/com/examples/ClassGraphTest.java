package com.examples;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClassGraphTest {

    @Test
    void testClassGraph() {
        String pkg = "com.examples.classgraph";
        try (ScanResult scanResult =
                     new ClassGraph()
                             //.verbose()               // Log to stderr
                             //.enableAllInfo()         // Scan classes, methods, fields, annotations
                             .acceptPackages(pkg)
                             .scan()) {
            for (ClassInfo routeClassInfo : scanResult.getAllClasses()) {
                assertEquals("class com.examples.classgraph.Car", routeClassInfo.loadClass().toString());
                assertEquals("@java.lang.Deprecated(forRemoval=false, since=\"\")", routeClassInfo.loadClass().getAnnotations()[0].toString());
                assertEquals("public com.examples.classgraph.Car()", routeClassInfo.loadClass().getConstructors()[0].toString());
                assertEquals("public com.examples.classgraph.Car(java.lang.String,java.lang.String)", routeClassInfo.loadClass().getConstructors()[1].toString());
            }
        }
    }
}

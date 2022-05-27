package com.examples;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

public class ClassGraphTest {

    @Test
    void testClassGraph() {
        String pkg = "com.examples";
        try (ScanResult scanResult =
                     new ClassGraph()
                             //.verbose()               // Log to stderr
                             //.enableAllInfo()         // Scan classes, methods, fields, annotations
                             .acceptPackages(pkg)
                             .scan()) {
            for (ClassInfo routeClassInfo : scanResult.getAllClasses()) {
                System.out.println("Class name: " + routeClassInfo.loadClass().toString());
                for (Annotation annotation :routeClassInfo.loadClass().getAnnotations()) {
                    System.out.println(" Annotation: " + annotation);
                }
                for (Constructor constructor :routeClassInfo.loadClass().getConstructors()) {
                    System.out.println("  Constructor: " + constructor);
                }
            }
        }
    }
}

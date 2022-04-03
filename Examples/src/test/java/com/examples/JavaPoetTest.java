package com.examples;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JavaPoetTest {
    private String expectedClass = """
            package com.examples;
            
            import java.lang.String;
            
            public final class GeneratedStudent {
              private String firstName;
            
              private String lastName;
            
              public GeneratedStudent(final String firstName, final String lastName) {
                this.firstName = firstName;
                this.lastName = lastName;
              }
            
              public void setFirstName(String firstName) {
                this.firstName = firstName;
              }
            
              public String getFirstName() {
                return firstName;
              }
            }
            """;

    @Test
    void testCreateClass() throws IOException {
        MethodSpec setFirstName = MethodSpec.methodBuilder("setFirstName")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(String.class, "firstName")
                .addStatement("this.$N = $N", "firstName", "firstName")
                .build();

        MethodSpec getFirstName = MethodSpec.methodBuilder("getFirstName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return firstName")
                .build();

        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addParameter(String.class, "firstName", Modifier.FINAL)
                .addParameter(String.class, "lastName", Modifier.FINAL)
                .addStatement("this.$N = $N", "firstName", "firstName")
                .addStatement("this.$N = $N", "lastName", "lastName")
                .addModifiers(Modifier.PUBLIC).build();


        TypeSpec student = TypeSpec.classBuilder("GeneratedStudent")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(String.class, "firstName", Modifier.PRIVATE)
                .addField(String.class, "lastName", Modifier.PRIVATE)
                .addMethod(setFirstName)
                .addMethod(getFirstName)
                .addMethod(constructor)
                .build();

        JavaFile javaFile = JavaFile.builder("com.examples", student)
                .build();

        assertEquals(expectedClass, javaFile.toString());

        javaFile.writeTo(System.out);

        Path resourceDirectory = Paths.get("src","test","java");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        javaFile.writeTo(resourceDirectory);
    }

    @Test
    void testStudent() throws IOException {
        GeneratedStudent generatedStudent = new GeneratedStudent("Johan", "Janssen");
        assertEquals("Johan", generatedStudent.getFirstName());
    }
}

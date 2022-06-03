package com.example;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EqualsVerifierTest {

    private String expectedErrorMessage = """
            EqualsVerifier found a problem in class com.example.EqualsVerifierTest$Student.
            -> hashCode: hashCodes should be equal:""";

    class Student {
        private String firstName;
        private String lastName;

        public Student(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Student student)) {
                return false;
            }
            return firstName == student.firstName && lastName == student.firstName;
        }
    }

    class FixedStudent {
        private final String firstName; // Add final
        private final String lastName; // Add final

        public FixedStudent(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public final boolean equals(Object o) { // Add final and  rewrite content
            if (this == o) return true;
            if (!(o instanceof FixedStudent)) return false;
            FixedStudent that = (FixedStudent) o;
            return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
        }

        @Override // Add hashcode
        public final int hashCode() {
            return Objects.hash(firstName, lastName);
        }
    }

    @Test
    public void testStudentEquals() {
        AssertionError assertionError = Assertions.assertThrows(AssertionError.class, () -> {
            EqualsVerifier.forClass(Student.class).verify();
        });

        assertTrue(assertionError.getMessage().startsWith(expectedErrorMessage));
    }

    @Test
    public void testFixedStudentEquals() {
        Assertions.assertDoesNotThrow(() -> {
            EqualsVerifier.forClass(FixedStudent.class).verify();
        });
    }
}

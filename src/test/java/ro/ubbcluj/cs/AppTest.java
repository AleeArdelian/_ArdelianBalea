package ro.ubbcluj.cs;

import org.junit.BeforeClass;
import org.junit.Test;
import ro.ubbcluj.cs.domain.Student;
import ro.ubbcluj.cs.repository.StudentXMLRepository;
import ro.ubbcluj.cs.service.Service;
import ro.ubbcluj.cs.validation.StudentValidator;
import ro.ubbcluj.cs.validation.Validator;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private static final String STUDENTS_FILE = "test-studenti.xml";

    private static final Validator<Student> studentValidator = new StudentValidator();

    private static Service service;

    @BeforeClass
    public static void init()  {
        final StudentXMLRepository studentRepository = new StudentXMLRepository(studentValidator, STUDENTS_FILE);
        studentRepository.delete("aaa1111");
        service = new Service(studentRepository, null, null);
    }

    @Test
    public void addStudent_validStudent_studentIsAdded() {
        // Given
        final String id = "aaa1111";
        final String nume = "Balea Alexandru";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 1);
    }

    @Test
    public void addStudent_emptyId_studentNotValid() {
        // Given
        final String id = "";
        final String nume = "Balea Alexandru";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_idAlreadyExists_studentNotAdded() {
        // Given
        final String id = "aaa1111";
        final String nume = "Ardelian Alexandra";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_emptyName_studentNotValid() {
        // Given
        final String id = "aaa1112";
        final String nume = "";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_nameWithNonAlphaCharacters_studentNotValid() {
        // Given
        final String id = "aaa1113";
        final String nume = "Balea Alexandru 121312312";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_groupNotPositive_studentNotValid() {
        // Given
        final String id = "aaa1114";
        final String nume = "Balea Alexandru";
        final int grupa = -431;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_groupLengthNot3_studentNotValid() {
        // Given
        final String id = "aaa1115";
        final String nume = "Balea Alexandru";
        final int grupa = 931123;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

}

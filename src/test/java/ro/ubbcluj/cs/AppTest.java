package ro.ubbcluj.cs;

import org.junit.BeforeClass;
import org.junit.Test;
import ro.ubbcluj.cs.domain.Student;
import ro.ubbcluj.cs.domain.Tema;
import ro.ubbcluj.cs.repository.AbstractCRUDRepository;
import ro.ubbcluj.cs.repository.StudentRepository;
import ro.ubbcluj.cs.repository.StudentXMLRepository;
import ro.ubbcluj.cs.repository.TemaRepository;
import ro.ubbcluj.cs.service.Service;
import ro.ubbcluj.cs.validation.StudentValidator;
import ro.ubbcluj.cs.validation.TemaValidator;
import ro.ubbcluj.cs.validation.Validator;

import static org.junit.Assert.*;

public class AppTest {

    private static final String STUDENTS_FILE = "test-studenti.xml";

    private static final Validator<Student> studentValidator = new StudentValidator();

    private static Service service;

    @BeforeClass
    public static void init() {
        final StudentXMLRepository studentRepository = new StudentXMLRepository(studentValidator, STUDENTS_FILE);
        studentRepository.delete("aaa1111");
        service = new Service(studentRepository, null, null);
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void saveStudent_ValidStudent_WorksFine() {
        final String id = "123";
        final String name = "Balea Alexandru";
        final int group = 931;
        final Student student = new Student(id, name, group);

        final Validator<Student> studentValidator = new StudentValidator();
        final AbstractCRUDRepository<String, Student> studentRepository = new StudentRepository(studentValidator);

        final Student savedStudent = studentRepository.save(student);
        final Student savedStudent1 = studentRepository.save(student);

        assertNull(savedStudent);
        assertEquals(savedStudent1, student);
    }

    @Test
    public void saveStudent_InvalidStudent_ThrowsValidatorException() {
        final String id = "";
        final String name = "Balea Alexandru";
        final int group = 931;
        final Student student = new Student(id, name, group);

        final Validator<Student> studentValidator = new StudentValidator();
        final AbstractCRUDRepository<String, Student> studentRepository = new StudentRepository(studentValidator);

        final Student savedStudent = studentRepository.save(student);

        assertNull(savedStudent);
    }

    @Test
    public void addAssignment_ValidAssignment_WorksFine() {
        final String id = "12";
        final String descriere = "Tema 1";
        final int deadline = 13;
        final int startline = 11;
        final Tema tema = new Tema(id, descriere, deadline, startline);

        final Validator<Tema> temaValidator = new TemaValidator();
        final AbstractCRUDRepository<String, Tema> temaRepository = new TemaRepository(temaValidator);

        final Tema savedTema = temaRepository.save(tema);

        assertNull(savedTema);
    }

    @Test
    public void addAssignment_InvalidAssignment_ThrowsValidatorException() {
        final String id = "12";
        final String descriere = "Tema 1";
        final int deadline = 11;
        final int startline = 14;
        final Tema tema = new Tema(id, descriere, deadline, startline);

        final Validator<Tema> temaValidator = new TemaValidator();
        final AbstractCRUDRepository<String, Tema> temaRepository = new TemaRepository(temaValidator);

        final Tema savedTema = temaRepository.save(tema);

        assertNull(savedTema);
    }

    @Test
    public void addStudent_TC1_emptyId_errorMessage() {
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
    public void addStudent_TC2_idLength1_studentIsAdded() {
        // Given
        final String id = "a";
        final String nume = "Balea Alexandru";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 1);
    }

    @Test
    public void addStudent_TC3_idLength3_studentIsAdded() {
        // Given
        final String id = "aaa";
        final String nume = "Balea Alexandru";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 1);
    }

    @Test
    public void addStudent_TC4_idLength7_studentIsAdded() {
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
    public void addStudent_TC5_idLength8_errorMessage() {
        // Given
        final String id = "aaaa1111";
        final String nume = "Balea Alexandru";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC6_idAlreadyExists_errorMessage() {
        // Given
        final String id = "aaaa111";
        final String nume = "Ardelian Alexandra";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC7_idLengthGreaterThan7_errorMessage() {
        // Given
        final String id = "aaaaaaa11111111";
        final String nume = "Balea Alexandru";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC8_emptyName_errorMessage() {
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
    public void addStudent_TC9_nameWithNonAlphaCharacters_errorMessage() {
        // Given
        final String id = "aaa1113";
        final String nume = "Balea Alexandru 12312412";
        final int grupa = 931;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC10_groupNotPositive_errorMessage() {
        // Given
        final String id = "aaa1114";
        final String nume = "Balea Alexandru";
        final int grupa = -432;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC11_groupZero_errorMessage() {
        // Given
        final String id = "aaa1115";
        final String nume = "Balea Alexandru";
        final int grupa = 0;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC12_groupMinus1_errorMessage() {
        // Given
        final String id = "aaa1116";
        final String nume = "Balea Alexandru";
        final int grupa = -1;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC13_group1_errorMessage() {
        // Given
        final String id = "aaa1117";
        final String nume = "Balea Alexandru";
        final int grupa = 1;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC14_group50_errorMessage() {
        // Given
        final String id = "aaa1118";
        final String nume = "Balea Alexandru";
        final int grupa = 50;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC15_group99_errorMessage() {
        // Given
        final String id = "aaa1119";
        final String nume = "Balea Alexandru";
        final int grupa = 99;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC16_group100_studentIsAdded() {
        // Given
        final String id = "aaa1120";
        final String nume = "Balea Alexandru";
        final int grupa = 100;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 1);
    }

    @Test
    public void addStudent_TC17_group101_studentIsAdded() {
        // Given
        final String id = "aaa1121";
        final String nume = "Balea Alexandru";
        final int grupa = 101;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 1);
    }

    @Test
    public void addStudent_TC19_group999_studentIsAdded() {
        // Given
        final String id = "aaa1123";
        final String nume = "Balea Alexandru";
        final int grupa = 999;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 1);
    }

    @Test
    public void addStudent_TC20_group1000_errorMessage() {
        // Given
        final String id = "aaa1124";
        final String nume = "Balea Alexandru";
        final int grupa = 1000;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC21_group1001_errorMessage() {
        // Given
        final String id = "aaa1125";
        final String nume = "Balea Alexandru";
        final int grupa = 1001;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

    @Test
    public void addStudent_TC22_group13400_errorMessage() {
        // Given
        final String id = "aaa1126";
        final String nume = "Balea Alexandru";
        final int grupa = 13400;

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, 0);
    }

}

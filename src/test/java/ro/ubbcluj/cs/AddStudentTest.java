package ro.ubbcluj.cs;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ro.ubbcluj.cs.domain.Student;
import ro.ubbcluj.cs.repository.StudentXMLRepository;
import ro.ubbcluj.cs.service.Service;
import ro.ubbcluj.cs.validation.StudentValidator;
import ro.ubbcluj.cs.validation.Validator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AddStudentTest {


    private static final String STUDENTS_FILE = "test-studenti.xml";

    private static final Validator<Student> studentValidator = new StudentValidator();

    private static Service service;

    @BeforeClass
    public static void init() {
        final StudentXMLRepository studentRepository = new StudentXMLRepository(studentValidator, STUDENTS_FILE);
        studentRepository.delete("aaa1111");
        service = new Service(studentRepository, null, null);
    }

    private String id;
    private String nume;
    private int grupa;
    private int expectedResult;

    public AddStudentTest(final String id, final String nume, final int grupa, final int expectedResult) {
        this.id = id;
        this.nume = nume;
        this.grupa = grupa;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"", "Balea Alexandru", 931, 0},
                {"a", "Balea Alexandru", 931, 1},
                {"aaa", "Balea Alexandru", 931, 1},
                {"aaa1111", "Balea Alexandru", 931, 1},
                {"aaaa1111", "Balea Alexandru", 931, 0},
                {"aaa1111", "Ardelian Alexandra", 931, 1},
                {"aaaaaaaaaa111111111", "Balea Alexandru", 931, 0},
                {"aaa1112", "", 931, 0},
                {"aaa1113", "Balea Alexandru 12312412", 931, 0},
                {"aaa1114", "Ardelian Alexandra", -432, 0},
                {"aaa1115", "Ardelian Alexandra", 0, 0},
                {"aaa1116", "Ardelian Alexandra", -1, 0},
                {"aaa1117", "Ardelian Alexandra", 1, 0},
                {"aaa1118", "Ardelian Alexandra", 50, 0},
                {"aaa1119", "Ardelian Alexandra", 99, 0},
                {"aaa1120", "Ardelian Alexandra", 100, 1},
                {"aaa1121", "Ardelian Alexandra", 101, 1},
                {"aaa1123", "Ardelian Alexandra", 999, 1},
                {"aaa1124", "Ardelian Alexandra", 1000, 0},
                {"aaa1125", "Ardelian Alexandra", 1001, 0},
                {"aaa1126", "Ardelian Alexandra", 13400, 0}
        });
    }

    @Test
    public void testAddStudentFeature() {

        // When
        final int result = service.saveStudent(id, nume, grupa);

        // Then
        assertEquals(result, expectedResult);
    }

}

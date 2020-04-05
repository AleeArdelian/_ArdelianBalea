package ro.ubbcluj.cs;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ro.ubbcluj.cs.domain.Tema;
import ro.ubbcluj.cs.repository.TemaXMLRepository;
import ro.ubbcluj.cs.service.Service;
import ro.ubbcluj.cs.validation.TemaValidator;
import ro.ubbcluj.cs.validation.Validator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AddAssignmentTest {


    private static final String ASSISGNMENT_FILE = "test-teme.xml";

    private static final Validator<Tema> temaValidator = new TemaValidator();

    private static Service service;
    private String id;
    private String descriere;
    private int startline;
    private int deadline;
    private int expectedResult;

    public AddAssignmentTest(final String id, final String descriere, final int startline, final int deadline, final int expectedResult) {
        this.id = id;
        this.descriere = descriere;
        this.startline = startline;
        this.deadline = deadline;
        this.expectedResult = expectedResult;
    }

    @BeforeClass
    public static void init() {
        final TemaXMLRepository temaXMLRepository = new TemaXMLRepository(temaValidator, ASSISGNMENT_FILE);
        service = new Service(null, temaXMLRepository, null);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", "asd", 13, 14, 0},
                {"aaa", "", 13, 14, 0},
                {"aaa", "asd", -1, 3, 0},
                {"aaa", "asd", 1, 17, 0},
                {"aaa", "asd", 12, 14, 1},
                {"aaa", "asd", 13, 17, 0},
                {"aaa", "asd", 16, 1, 0},
                {"aaa", "asd", 13, -4, 0},
                {"aaa", "asd", 10, 12, 1},
        });
    }

    @Test
    public void testAddStudentFeature() {

        // When
        final int result = service.saveTema(id, descriere, deadline, startline);

        // Then
        assertEquals(result, expectedResult);
    }

}

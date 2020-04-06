package ro.ubbcluj.cs;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ro.ubbcluj.cs.domain.Nota;
import ro.ubbcluj.cs.domain.Student;
import ro.ubbcluj.cs.domain.Tema;
import ro.ubbcluj.cs.repository.NotaXMLRepository;
import ro.ubbcluj.cs.repository.StudentXMLRepository;
import ro.ubbcluj.cs.repository.TemaXMLRepository;
import ro.ubbcluj.cs.service.Service;
import ro.ubbcluj.cs.validation.NotaValidator;
import ro.ubbcluj.cs.validation.StudentValidator;
import ro.ubbcluj.cs.validation.TemaValidator;
import ro.ubbcluj.cs.validation.Validator;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AddGradeTest {


    private static final String STUDENTS_FILE = "test-note-studenti.xml";
    private static final String ASSISGNMENT_FILE = "test-note-kteme.xml";
    private static final String GREADE_FILE = "test-note.xml";


    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Tema> temaValidator = new TemaValidator();
    private static final Validator<Nota> notaValidator = new NotaValidator();

    private static Service service;

    private String idStudent;
    private String idTema;
    private double valNota;
    private int predata;
    private String feedback;
    private int expectedResult;

    public AddGradeTest(final String idStudent, final String idTema, final double valNota, final int predata,
                        final String feedback, final int expectedResult) {
        this.idStudent = idStudent;
        this.idTema = idTema;
        this.valNota = valNota;
        this.predata = predata;
        this.feedback = feedback;
        this.expectedResult = expectedResult;
    }

    @BeforeClass
    public static void init() {
        final StudentXMLRepository studentRepository = new StudentXMLRepository(studentValidator, STUDENTS_FILE);
        final TemaXMLRepository temaXMLRepository = new TemaXMLRepository(temaValidator, ASSISGNMENT_FILE);
        final NotaXMLRepository notaXMLRepository = new NotaXMLRepository(notaValidator, GREADE_FILE);

        service = new Service(studentRepository, temaXMLRepository, notaXMLRepository);

        studentRepository.save(new Student("aaa111", "Balea Alexandru", 931));
        studentRepository.save(new Student("aaa112", "Ardelian Alexandra", 931));
        studentRepository.save(new Student("aaa113", "Belcianu Catalin", 931));
        temaXMLRepository.save(new Tema("1", "Tema 1", 3, 1));
        temaXMLRepository.save(new Tema("2", "Tema 2", 4, 2));
        temaXMLRepository.save(new Tema("3", "Tema 3", 5, 3));

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"aaa111", "3", 7, 13, "buna", 0},
                {"aaa111", "", 7, 13, "idtema empty", -1},
                {"", "3", 7, 13, "idstudentempty", -1},
                {"aaa111", "3", 7, 13, "", 0},
                {"aaa111", "3", 7, 13, "idduplicat", 0},
                {"aaa112", "2", -1, 13, "nota<0", 0},
                {"aaa111", "3", 459, 13, "noda>10", 0},
                {"aaa113", "3", 10, 236, "saptaman>14", 0},
                {"aaa113", "2", 2, -20, "saptamana<0", 0}

        });
    }

    @Test
    public void testAddGrade() {

        // When
        final int result = service.saveNota(idStudent, idTema, valNota, predata, feedback);

        // Then
        assertEquals(result, expectedResult);
    }

}

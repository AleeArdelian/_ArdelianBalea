package ro.ubbcluj.cs;

import org.junit.BeforeClass;
import org.junit.Test;
import ro.ubbcluj.cs.domain.Nota;
import ro.ubbcluj.cs.domain.Student;
import ro.ubbcluj.cs.domain.Tema;
import ro.ubbcluj.cs.repository.*;
import ro.ubbcluj.cs.service.Service;
import ro.ubbcluj.cs.validation.NotaValidator;
import ro.ubbcluj.cs.validation.StudentValidator;
import ro.ubbcluj.cs.validation.TemaValidator;
import ro.ubbcluj.cs.validation.Validator;

import static org.junit.Assert.*;

public class IntegrationTest {

    private static final String STUDENTS_FILE = "test-students.xml";
    private static final String ASSIGNMENTS_FILE = "test-assignments.xml";
    private static final String GRADES_FILE = "test-grades.xml";

    private static final Validator<Student> studentValidator = new StudentValidator();
    private static final Validator<Tema> assignmentValidator = new TemaValidator();
    private static final Validator<Nota> gradeValidator = new NotaValidator();


    private static Service service;

    @BeforeClass
    public static void init() {
        final StudentXMLRepository studentRepository = new StudentXMLRepository(studentValidator, STUDENTS_FILE);
        final TemaXMLRepository assignmentRepository = new TemaXMLRepository(assignmentValidator, ASSIGNMENTS_FILE);
        final NotaXMLRepository gradeRepository = new NotaXMLRepository(gradeValidator, GRADES_FILE);

        service = new Service(studentRepository, assignmentRepository, gradeRepository);
    }

    @Test
    public void addStudent() {
        final String id = "aaa111";
        final String name = "Balea Alexandru";
        final int group = 931;

        final int result = service.saveStudent(id, name, group);

        assertEquals(result, 1);
    }

    @Test
    public void addStudentAndAssignment() {
        final String studentId = "aaa112";
        final String name = "Ardelian Alexandra";
        final int group = 931;

        final String temaId = "1";
        final String descriere = "Pentru 5";
        final int deadline = 10;
        final int startline = 7;

        final int addStudentResult = service.saveStudent(studentId, name, group);
        final int addTemaResult = service.saveTema(temaId, descriere, deadline, startline);

        assertEquals(addStudentResult, 1);
        assertEquals(addTemaResult, 1);
    }

    @Test
    public void addStudentAndAssignmentAndGrade() {
        final String studentId = "aaa112";
        final String name = "Ardelian Alexandra";
        final int group = 931;

        final String temaId = "1";
        final String descriere = "Pentru 5";
        final int deadline = 10;
        final int startline = 7;

        final String gradeId = "3";
        final double valNota = 10;
        final int predata = 10;
        final String feedback = "Ia-ti familia si fa 3 ture";

        final int addStudentResult = service.saveStudent(studentId, name, group);
        final int addTemaResult = service.saveTema(temaId, descriere, deadline, startline);
        final int addGradeForStudent = service.saveNota(studentId, temaId, valNota, predata, feedback);

        assertEquals(addStudentResult, 1);
        assertEquals(addTemaResult, 1);
        assertEquals(addGradeForStudent, 0);
    }
}

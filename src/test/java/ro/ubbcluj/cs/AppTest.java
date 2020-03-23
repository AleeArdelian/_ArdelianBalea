package ro.ubbcluj.cs;

import org.junit.Test;
import ro.ubbcluj.cs.domain.Student;
import ro.ubbcluj.cs.repository.AbstractCRUDRepository;
import ro.ubbcluj.cs.repository.StudentRepository;
import ro.ubbcluj.cs.validation.StudentValidator;
import ro.ubbcluj.cs.validation.Validator;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
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
}

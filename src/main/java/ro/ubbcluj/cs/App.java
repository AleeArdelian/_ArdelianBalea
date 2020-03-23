package ro.ubbcluj.cs;

import ro.ubbcluj.cs.console.UI;
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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        UI consola = new UI(service);
        consola.run();

        //PENTRU GUI
        // de avut un check: daca profesorul introduce sau nu saptamana la timp
        // daca se introduce nota la timp, se preia saptamana din sistem
        // altfel, se introduce de la tastatura
    }
}

package ro.ubbcluj.cs.validation;
import ro.ubbcluj.cs.domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        if (student.getID() == null || student.getID().equals("") || student.getID().length() > 7) {
            throw new ValidationException("ID invalid! \n");
        }
        if (student.getNume() == null || student.getNume().equals("") || !student.getNume().matches("(^[a-zA-Z ]*$)")) {
            throw new ValidationException("Nume invalid! \n");
        }
        if (student.getGrupa() < 100 || student.getGrupa() > 999) {
            throw new ValidationException("Grupa invalida! \n");
        }
    }
}


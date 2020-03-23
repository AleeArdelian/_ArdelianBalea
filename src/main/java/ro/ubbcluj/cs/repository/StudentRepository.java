package ro.ubbcluj.cs.repository;

import ro.ubbcluj.cs.domain.Student;
import ro.ubbcluj.cs.validation.*;

public class StudentRepository extends AbstractCRUDRepository<String, Student> {
    public StudentRepository(Validator<Student> validator) {
        super(validator);
    }
}


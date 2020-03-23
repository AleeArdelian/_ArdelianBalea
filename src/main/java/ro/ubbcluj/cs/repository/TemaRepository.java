package ro.ubbcluj.cs.repository;

import ro.ubbcluj.cs.domain.Tema;
import ro.ubbcluj.cs.validation.*;

public class TemaRepository extends AbstractCRUDRepository<String, Tema> {
    public TemaRepository(Validator<Tema> validator) {
        super(validator);
    }
}


package ro.ubbcluj.cs.repository;

import ro.ubbcluj.cs.domain.*;
import ro.ubbcluj.cs.validation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NotaRepository extends AbstractCRUDRepository<Pair<String, String>, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}

package ro.ubbcluj.cs.validation;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}

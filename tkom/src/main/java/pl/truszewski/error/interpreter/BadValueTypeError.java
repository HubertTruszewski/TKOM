package pl.truszewski.error.interpreter;

public class BadValueTypeError extends InterpreterError {
    public BadValueTypeError(final String message) {
        super(message);
    }
}

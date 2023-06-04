package pl.truszewski.error.interpreter;

public class MissingReturnedValueError extends InterpreterError {
    public MissingReturnedValueError(final String message) {
        super(message);
    }
}

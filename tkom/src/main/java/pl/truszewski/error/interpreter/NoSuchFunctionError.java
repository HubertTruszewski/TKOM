package pl.truszewski.error.interpreter;

public class NoSuchFunctionError extends InterpreterError {
    public NoSuchFunctionError(final String message) {
        super(message);
    }
}

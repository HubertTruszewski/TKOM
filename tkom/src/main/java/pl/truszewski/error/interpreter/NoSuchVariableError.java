package pl.truszewski.error.interpreter;

public class NoSuchVariableError extends InterpreterError {
    public NoSuchVariableError(final String message) {
        super(message);
    }
}

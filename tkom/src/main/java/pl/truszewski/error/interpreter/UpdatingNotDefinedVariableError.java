package pl.truszewski.error.interpreter;

public class UpdatingNotDefinedVariableError extends InterpreterError {
    public UpdatingNotDefinedVariableError(final String message) {
        super(message);
    }
}

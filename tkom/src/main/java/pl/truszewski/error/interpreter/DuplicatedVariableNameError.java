package pl.truszewski.error.interpreter;

public class DuplicatedVariableNameError extends InterpreterError {

    public DuplicatedVariableNameError(final String message) {
        super(message);
    }
}

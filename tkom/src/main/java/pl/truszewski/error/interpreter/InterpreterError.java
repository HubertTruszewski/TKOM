package pl.truszewski.error.interpreter;

public abstract class InterpreterError extends RuntimeException {
    private final String message;

    protected InterpreterError(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

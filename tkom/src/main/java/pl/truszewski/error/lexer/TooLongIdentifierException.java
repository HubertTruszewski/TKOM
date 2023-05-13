package pl.truszewski.error.lexer;

public class TooLongIdentifierException extends Exception {
    public TooLongIdentifierException(String message) {
        super(message);
    }

}

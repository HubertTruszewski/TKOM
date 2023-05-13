package pl.truszewski.error.lexer;

public class TooLongStringException extends Exception {
    public TooLongStringException(String message) {
        super(message);
    }
}

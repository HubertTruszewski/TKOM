package pl.truszewski.error;

public class TooLongStringException extends Exception {
    public TooLongStringException(String message) {
        super(message);
    }
}

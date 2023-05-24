package pl.truszewski.error.lexer;

public class MissingSecondTokenCharacter extends Exception {
    public MissingSecondTokenCharacter(String message) {
        super(message);
    }

}

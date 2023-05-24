package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class MissingCharacterError extends ParserError {
    private final String character;

    public MissingCharacterError(String message, String character, Position position) {
        super(message, position);
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}

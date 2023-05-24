package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class MissingIdentifierError extends ParserError {
    public MissingIdentifierError(final String message, final Position position) {
        super(message, position);
    }
}

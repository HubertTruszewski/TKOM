package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class MissingTypeError extends ParserError {
    public MissingTypeError(final String message, final Position position) {
        super(message, position);
    }
}

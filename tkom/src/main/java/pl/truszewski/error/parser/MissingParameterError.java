package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class MissingParameterError extends ParserError {
    public MissingParameterError(final String message, final Position position) {
        super(message, position);
    }
}

package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class MissingExpressionError extends ParserError {

    public MissingExpressionError(final String message, final Position position) {
        super(message, position);
    }
}

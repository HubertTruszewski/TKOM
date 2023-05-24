package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class CannotAssignValueToExpressionError extends ParserError {
    public CannotAssignValueToExpressionError(final String message, final Position position) {
        super(message, position);
    }
}

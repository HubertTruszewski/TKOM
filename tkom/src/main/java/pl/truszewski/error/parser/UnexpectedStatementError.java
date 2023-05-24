package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class UnexpectedStatementError extends ParserError {
    public UnexpectedStatementError(final String message, final Position position) {
        super(message, position);
    }
}

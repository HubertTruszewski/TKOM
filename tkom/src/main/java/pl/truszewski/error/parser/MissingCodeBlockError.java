package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class MissingCodeBlockError extends ParserError {
    public MissingCodeBlockError(final String message, final Position position) {
        super(message, position);
    }
}

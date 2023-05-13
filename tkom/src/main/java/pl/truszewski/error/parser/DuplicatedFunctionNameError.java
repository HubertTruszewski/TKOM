package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class DuplicatedFunctionNameError extends ParserError {
    public DuplicatedFunctionNameError(final String message, final Position position) {
        super(message, position);
    }
}

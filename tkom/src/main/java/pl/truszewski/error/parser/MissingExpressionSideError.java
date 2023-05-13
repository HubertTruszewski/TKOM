package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class MissingExpressionSideError extends ParserError {
    private final String missingSide;

    public MissingExpressionSideError(final String message, final String missingSide, final Position position) {
        super(message, position);
        this.missingSide = missingSide;
    }

    public String getMissingSide() {
        return missingSide;
    }
}

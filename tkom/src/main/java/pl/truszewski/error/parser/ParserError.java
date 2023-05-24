package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public abstract class ParserError extends Exception {
    private final Position position;
    private final String message;

    protected ParserError(final String message, final Position position) {
        this.message = message;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package pl.truszewski.error.parser;

import pl.truszewski.token.Position;

public class DuplicatedParameterError extends ParserError {
    private final String name;
    public DuplicatedParameterError(final String message, final String name, final Position position) {
        super(message, position);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

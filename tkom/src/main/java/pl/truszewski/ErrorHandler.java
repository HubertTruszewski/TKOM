package pl.truszewski;

import pl.truszewski.error.parser.ParserError;
import pl.truszewski.token.Position;

public class ErrorHandler {
    public void handleError(Exception exception, Position position) {
        System.out.println(exception.getMessage() + ". Position: " + position.toString());
    }

    public void handleParserError(ParserError error) {
        System.out.println(error.getMessage() + ". Position: " + error.getPosition().toString());
    }

}

package pl.truszewski;

import lombok.extern.slf4j.Slf4j;
import pl.truszewski.error.parser.ParserError;
import pl.truszewski.token.Position;

@Slf4j
public class ErrorHandler {
    public void handleError(Exception exception, Position position) {
        log.error(exception.getMessage() + ". Position: " + position.toString());
    }

    public void handleParserError(ParserError error) {
        log.error(error.getMessage() + ". Position: " + error.getPosition().toString());
    }

}

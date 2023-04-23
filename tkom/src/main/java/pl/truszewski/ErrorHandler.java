package pl.truszewski;

import pl.truszewski.token.Position;

public class ErrorHandler {

    public void handleError(Exception exception, Position position) {
        System.out.println(exception.getMessage() + ". Position: " + position.toString());
    }

}

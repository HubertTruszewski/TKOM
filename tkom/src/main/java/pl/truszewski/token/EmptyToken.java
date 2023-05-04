package pl.truszewski.token;

public class EmptyToken extends Token {

    public EmptyToken(TokenType tokenType, Position position) {
        super(tokenType, position, null);
    }

}

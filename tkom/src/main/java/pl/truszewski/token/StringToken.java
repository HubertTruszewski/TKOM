package pl.truszewski.token;

public class StringToken extends Token {

    public StringToken(TokenType tokenType, String value, Position position) {
        super(tokenType, position, value);
    }

    @Override
    public String toString() {
        return "StringToken{}" + super.toString();
    }
}

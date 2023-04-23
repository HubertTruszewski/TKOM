package pl.truszewski.token;

public class DoubleToken extends Token {

    public DoubleToken(Double value, Position position) {
        super(TokenType.DOUBLE_NUMBER, position, value);
    }

}

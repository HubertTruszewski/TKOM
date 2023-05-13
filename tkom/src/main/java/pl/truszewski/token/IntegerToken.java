package pl.truszewski.token;

public class IntegerToken extends Token {

    public IntegerToken(Integer value, Position position) {
        super(TokenType.INT_NUMBER, position, value);
    }

    @Override
    public String toString() {
        return "IntegerToken{}" + super.toString();
    }
}

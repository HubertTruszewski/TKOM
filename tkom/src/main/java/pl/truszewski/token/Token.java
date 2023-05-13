package pl.truszewski.token;

public abstract class Token {
    private TokenType tokenType;
    private Position position;
    private Object value;

    public Token(TokenType tokenType, Position position, Object value) {
        this.tokenType = tokenType;
        this.position = position;
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Position getPosition() {
        return position;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" + "tokenType=" + tokenType + ", position=" + position + ", value=" + value + '}';
    }
}

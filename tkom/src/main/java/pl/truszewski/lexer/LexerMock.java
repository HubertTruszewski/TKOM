package pl.truszewski.lexer;

import java.util.List;

import pl.truszewski.token.Token;

public class LexerMock implements Lexer{
    private final List<Token> tokens;

    public LexerMock(final List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Token next() {
        if (!tokens.isEmpty())
            return tokens.remove(0);
        return null;
    }
}

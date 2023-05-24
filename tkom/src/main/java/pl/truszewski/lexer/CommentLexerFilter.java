package pl.truszewski.lexer;

import pl.truszewski.token.Token;
import pl.truszewski.token.TokenType;

public class CommentLexerFilter implements Lexer {
    private final Lexer lexer;

    public CommentLexerFilter(final Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public Token next() {
        Token token = lexer.next();
        while (token.getTokenType() == TokenType.COMMENT) {
            token = lexer.next();
        }
        return token;
    }
}

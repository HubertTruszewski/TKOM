package pl.truszewski.lexer;

import pl.truszewski.token.Token;

public interface Lexer {
    Token token();

    void next();

}

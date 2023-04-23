package pl.truszewski;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import pl.truszewski.lexer.Lexer;
import pl.truszewski.lexer.LexerImpl;
import pl.truszewski.source.Source;

public class App {
    public static void main(String[] args) throws IOException {
        Reader reader = new StringReader("   .  , } ");
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        lexer.next();
        var a = lexer.token();

        return;
    }
}

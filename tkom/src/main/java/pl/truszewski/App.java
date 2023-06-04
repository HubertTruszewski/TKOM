package pl.truszewski;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import pl.truszewski.interpreter.Interpreter;
import pl.truszewski.interpreter.InterpreterImpl;
import pl.truszewski.lexer.CommentLexerFilter;
import pl.truszewski.lexer.Lexer;
import pl.truszewski.lexer.LexerImpl;
import pl.truszewski.parser.Parser;
import pl.truszewski.parser.ParserImpl;
import pl.truszewski.programstructure.basic.Program;
import pl.truszewski.source.Source;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument provided!");
            return;
        }
        String fileName = args[0];
        try (FileReader fileReader = new FileReader(fileName)) {
            Reader reader = new BufferedReader(fileReader);
            ErrorHandler errorHandler = new ErrorHandler();
            Source source = new Source(reader, errorHandler);
            Lexer lexer = new LexerImpl(source, errorHandler);
            Lexer commentLexerFilter = new CommentLexerFilter(lexer);
            Parser parser = new ParserImpl(commentLexerFilter, errorHandler);
            Program program = parser.parse();
            Interpreter interpreter = new InterpreterImpl(System.out);
            interpreter.execute(program);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

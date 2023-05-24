package pl.truszewski;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import lombok.extern.slf4j.Slf4j;
import pl.truszewski.lexer.CommentLexerFilter;
import pl.truszewski.lexer.Lexer;
import pl.truszewski.lexer.LexerImpl;
import pl.truszewski.parser.Parser;
import pl.truszewski.parser.ParserImpl;
import pl.truszewski.programstructure.basic.Program;
import pl.truszewski.source.Source;
import pl.truszewski.visitor.PrinterVisitor;
import pl.truszewski.visitor.Visitor;

@Slf4j
public class App {
    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("tkom/src/main/resources/testfile.txt")) {
            Reader reader = new BufferedReader(fileReader);
            ErrorHandler errorHandler = new ErrorHandler();
            Source source = new Source(reader, errorHandler);
            Lexer lexer = new LexerImpl(source, errorHandler);
            Lexer commentLexerFilter = new CommentLexerFilter(lexer);
            Parser parser = new ParserImpl(commentLexerFilter, errorHandler);
            Program program = parser.parse();
            Visitor visitor = new PrinterVisitor();
            visitor.visit(program);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}

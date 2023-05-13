package pl.truszewski.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.truszewski.ErrorHandler;
import pl.truszewski.lexer.Lexer;
import pl.truszewski.lexer.LexerMock;
import pl.truszewski.programstructure.basic.FunctionDefinition;
import pl.truszewski.programstructure.basic.Program;
import pl.truszewski.programstructure.basic.ValueType;
import pl.truszewski.token.DoubleToken;
import pl.truszewski.token.EmptyToken;
import pl.truszewski.token.Position;
import pl.truszewski.token.StringToken;
import pl.truszewski.token.Token;
import pl.truszewski.token.TokenType;

public class ParserImplTest {

    @Test
    public void parseEmptyFunction() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.INTEGER, new Position()),
                new StringToken(TokenType.IDENTIFIER, "suma", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        assertEquals(1, program.functions().size());
        assertTrue(program.functions().containsKey("suma"));
        FunctionDefinition functionDefinition = program.functions().get("suma");
        assertTrue(functionDefinition.parameters().isEmpty());
        assertTrue(functionDefinition.block().statements().isEmpty());
        assertEquals("suma", functionDefinition.name());
        assertEquals(ValueType.INT, functionDefinition.returnValueType());
    }

    @Test
    public void parseSimpleFunctionWithParameters() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.INTEGER, new Position()),
                new StringToken(TokenType.IDENTIFIER, "function", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.INTEGER, new Position()),
                new StringToken(TokenType.IDENTIFIER, "abc", new Position()),
                new EmptyToken(TokenType.COMMA, new Position()),
                new EmptyToken(TokenType.STRING, new Position()),
                new StringToken(TokenType.IDENTIFIER, "text", new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.DOUBLE, new Position()),
                new StringToken(TokenType.IDENTIFIER, "num", new Position()),
                new EmptyToken(TokenType.EQUAL_SIGN, new Position()),
                new DoubleToken(24.56, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        assertEquals(1, program.functions().size());
        assertTrue(program.functions().containsKey("function"));
        FunctionDefinition functionDefinition = program.functions().get("function");
        assertEquals(2, functionDefinition.parameters().size());
        assertEquals(1, functionDefinition.block().statements().size());
        assertEquals("function", functionDefinition.name());
        assertEquals(ValueType.INT, functionDefinition.returnValueType());
    }

}

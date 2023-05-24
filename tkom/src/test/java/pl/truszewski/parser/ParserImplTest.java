package pl.truszewski.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.truszewski.ErrorHandler;
import pl.truszewski.lexer.Lexer;
import pl.truszewski.lexer.LexerMock;
import pl.truszewski.programstructure.basic.Block;
import pl.truszewski.programstructure.basic.FunctionDefinition;
import pl.truszewski.programstructure.basic.Program;
import pl.truszewski.programstructure.basic.ValueType;
import pl.truszewski.programstructure.expression.AccessExpression;
import pl.truszewski.programstructure.expression.AddExpression;
import pl.truszewski.programstructure.expression.AndExpression;
import pl.truszewski.programstructure.expression.CastingExpression;
import pl.truszewski.programstructure.expression.DivideExpression;
import pl.truszewski.programstructure.expression.DoubleNumber;
import pl.truszewski.programstructure.expression.EqualityExpression;
import pl.truszewski.programstructure.expression.FunctionCall;
import pl.truszewski.programstructure.expression.GreaterEqualExpression;
import pl.truszewski.programstructure.expression.GreaterExpression;
import pl.truszewski.programstructure.expression.IdentifierExpression;
import pl.truszewski.programstructure.expression.InequalityExpression;
import pl.truszewski.programstructure.expression.IntNumber;
import pl.truszewski.programstructure.expression.LessEqualExpression;
import pl.truszewski.programstructure.expression.LessExpression;
import pl.truszewski.programstructure.expression.MultiplyExpression;
import pl.truszewski.programstructure.expression.NegationExpression;
import pl.truszewski.programstructure.expression.OrExpression;
import pl.truszewski.programstructure.expression.StringExpression;
import pl.truszewski.programstructure.expression.SubtractExpression;
import pl.truszewski.programstructure.statements.AssignmentStatement;
import pl.truszewski.programstructure.statements.DeclarationStatement;
import pl.truszewski.programstructure.statements.IfStatement;
import pl.truszewski.programstructure.statements.WhileStatement;
import pl.truszewski.token.DoubleToken;
import pl.truszewski.token.EmptyToken;
import pl.truszewski.token.IntegerToken;
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

    @Test
    public void parseFunctionWithIfStatementWithElseBlock() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.IF, new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "x", new Position()),
                new EmptyToken(TokenType.GREATER_OR_EQUAL, new Position()),
                new IntegerToken(10, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "funCall", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.ELSE, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "anotherFunCall", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        assertEquals(1, program.functions().size());
        assertTrue(program.functions().containsKey("testFun"));
        FunctionDefinition testFun = program.functions().get("testFun");
        assertEquals(ValueType.VOID, testFun.returnValueType());
        assertEquals("testFun", testFun.name());
        assertTrue(testFun.parameters().isEmpty());
        assertTrue(testFun.block().statements().get(0) instanceof IfStatement);
        IfStatement ifStatement = (IfStatement) testFun.block().statements().get(0);
        assertEquals(new GreaterEqualExpression(new IdentifierExpression("x"), new IntNumber(10)),
                ifStatement.expression());
        assertNotNull(ifStatement.trueBlock());
        assertEquals(1, ifStatement.trueBlock().statements().size());
        assertEquals(new FunctionCall("funCall", new ArrayList<>()), ifStatement.trueBlock().statements().get(0));
        assertNotNull(ifStatement.elseBlock());
        assertEquals(1, ifStatement.elseBlock().statements().size());
        assertEquals(new FunctionCall("anotherFunCall", new ArrayList<>()),
                ifStatement.elseBlock().statements().get(0));
    }

    @Test
    public void parseFunctionWithIfStatementWithoutElseBlock() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.IF, new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "x", new Position()),
                new EmptyToken(TokenType.GREATER_OR_EQUAL, new Position()),
                new IntegerToken(10, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "funCall", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        assertEquals(1, program.functions().size());
        assertTrue(program.functions().containsKey("testFun"));
        FunctionDefinition testFun = program.functions().get("testFun");
        assertEquals(ValueType.VOID, testFun.returnValueType());
        assertEquals("testFun", testFun.name());
        assertTrue(testFun.parameters().isEmpty());
        assertTrue(testFun.block().statements().get(0) instanceof IfStatement);
        IfStatement ifStatement = (IfStatement) testFun.block().statements().get(0);
        assertEquals(new GreaterEqualExpression(new IdentifierExpression("x"), new IntNumber(10)),
                ifStatement.expression());
        assertNotNull(ifStatement.trueBlock());
        assertEquals(1, ifStatement.trueBlock().statements().size());
        assertEquals(new FunctionCall("funCall", new ArrayList<>()), ifStatement.trueBlock().statements().get(0));
        assertNull(ifStatement.elseBlock());
    }

    @Test
    public void parseFunctionWithWhileStatement() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.WHILE, new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "x", new Position()),
                new EmptyToken(TokenType.NOT_EQUALS, new Position()),
                new IntegerToken(20, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "i", new Position()),
                new EmptyToken(TokenType.EQUAL_SIGN, new Position()),
                new StringToken(TokenType.IDENTIFIER, "i", new Position()),
                new EmptyToken(TokenType.PLUS_SIGN, new Position()),
                new IntegerToken(1, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        assertEquals(1, program.functions().size());
        assertTrue(program.functions().containsKey("testFun"));
        FunctionDefinition testFun = program.functions().get("testFun");
        assertEquals(ValueType.VOID, testFun.returnValueType());
        assertEquals("testFun", testFun.name());
        assertTrue(testFun.block().statements().get(0) instanceof WhileStatement);
        WhileStatement whileStatement = (WhileStatement) testFun.block().statements().get(0);
        assertEquals(new InequalityExpression(new IdentifierExpression("x"), new IntNumber(20)),
                whileStatement.expression());
        assertEquals(1, whileStatement.codeBlock().statements().size());
        assertEquals(new AssignmentStatement("i", new AddExpression(new IdentifierExpression("i"), new IntNumber(1))),
                whileStatement.codeBlock().statements().get(0));
    }

    @Test
    public void testVariableDeclaration() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.INTEGER, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.EQUAL_SIGN, new Position()),
                new IntegerToken(4, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof DeclarationStatement);
        assertEquals(new DeclarationStatement(ValueType.INT, "a", new IntNumber(4)), block.statements().get(0));
    }

    @Test
    public void testAssignment() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.EQUAL_SIGN, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof AssignmentStatement);
        assertEquals(new AssignmentStatement("a", new DoubleNumber(4.5)), block.statements().get(0));
    }

    @Test
    public void testOrExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.EQUALS, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.OR, new Position()),
                new StringToken(TokenType.IDENTIFIER, "b", new Position()),
                new EmptyToken(TokenType.LESS_SIGN, new Position()),
                new DoubleToken(10.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof OrExpression);
        assertEquals(new OrExpression(new EqualityExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                new LessExpression(new IdentifierExpression("b"), new DoubleNumber(10.5))), block.statements().get(0));
    }

    @Test
    public void testAndExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.EQUALS, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.AND, new Position()),
                new StringToken(TokenType.IDENTIFIER, "b", new Position()),
                new EmptyToken(TokenType.LESS_SIGN, new Position()),
                new DoubleToken(10.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof AndExpression);
        assertEquals(new AndExpression(new EqualityExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                new LessExpression(new IdentifierExpression("b"), new DoubleNumber(10.5))), block.statements().get(0));
    }

    @Test
    public void testEqualityExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.EQUALS, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof EqualityExpression);
        assertEquals(new EqualityExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testInequalityExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.NOT_EQUALS, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof InequalityExpression);
        assertEquals(new InequalityExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testGreaterExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.GREATER_SIGN, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof GreaterExpression);
        assertEquals(new GreaterExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testGreaterEqualExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.GREATER_OR_EQUAL, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof GreaterEqualExpression);
        assertEquals(new GreaterEqualExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testLessExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.LESS_SIGN, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof LessExpression);
        assertEquals(new LessExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testLessEqualExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.LESS_OR_EQUAL, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof LessEqualExpression);
        assertEquals(new LessEqualExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testAdditionExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.PLUS_SIGN, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof AddExpression);
        assertEquals(new AddExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testSubtractExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.MINUS_SIGN, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof SubtractExpression);
        assertEquals(new SubtractExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testMultiplicationExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.ASTERISK_SIGN, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof MultiplyExpression);
        assertEquals(new MultiplyExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testDivideExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.SLASH, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof DivideExpression);
        assertEquals(new DivideExpression(new IdentifierExpression("a"), new DoubleNumber(4.5)),
                block.statements().get(0));
    }

    @Test
    public void testCastingExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.AS, new Position()),
                new EmptyToken(TokenType.INTEGER, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof CastingExpression);
        assertEquals(new CastingExpression(new IdentifierExpression("a"), ValueType.INT), block.statements().get(0));
    }

    @Test
    public void testNegationExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EXCLAMATION_MARK, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof NegationExpression);
        assertEquals(new NegationExpression(new IdentifierExpression("a")), block.statements().get(0));
    }

    @Test
    public void testNegationWithNumberExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.MINUS_SIGN, new Position()),
                new DoubleToken(3.4, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof NegationExpression);
        assertEquals(new NegationExpression(new DoubleNumber(3.4)), block.statements().get(0));
    }

    @Test
    public void testAccessExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.DOT, new Position()),
                new StringToken(TokenType.IDENTIFIER, "r", new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof AccessExpression);
        assertEquals(new AccessExpression(new IdentifierExpression("a"), new IdentifierExpression("r")),
                block.statements().get(0));
    }

    @Test
    public void testMultiAccessExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "a", new Position()),
                new EmptyToken(TokenType.DOT, new Position()),
                new StringToken(TokenType.IDENTIFIER, "r", new Position()),
                new EmptyToken(TokenType.DOT, new Position()),
                new StringToken(TokenType.IDENTIFIER, "x", new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof AccessExpression);
        assertEquals(new AccessExpression(new AccessExpression(new IdentifierExpression("a"),
                new IdentifierExpression("r")), new IdentifierExpression("x")), block.statements().get(0));
    }

    @Test
    public void testNumbersExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new DoubleToken(4.5, new Position()),
                new EmptyToken(TokenType.PLUS_SIGN, new Position()),
                new IntegerToken(2, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertEquals(new AddExpression(new DoubleNumber(4.5), new IntNumber(2)), block.statements().get(0));
    }

    @Test
    public void testFunctionCall() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.IDENTIFIER, "fun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertEquals(new FunctionCall("fun", List.of()), block.statements().get(0));
    }

    @Test
    public void testTextExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new StringToken(TokenType.TEXT, "fun", new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertEquals(new StringExpression("fun"), block.statements().get(0));
    }

    @Test
    public void testNestedExpression() {
        List<Token> tokenList = new ArrayList<>(Arrays.asList(new EmptyToken(TokenType.VOID, new Position()),
                new StringToken(TokenType.IDENTIFIER, "testFun", new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.LEFT_ROUND_BRACKET, new Position()),
                new IntegerToken(2, new Position()),
                new EmptyToken(TokenType.PLUS_SIGN, new Position()),
                new IntegerToken(4, new Position()),
                new EmptyToken(TokenType.RIGHT_ROUND_BRACKET, new Position()),
                new EmptyToken(TokenType.ASTERISK_SIGN, new Position()),
                new IntegerToken(5, new Position()),
                new EmptyToken(TokenType.SEMICOLON, new Position()),
                new EmptyToken(TokenType.RIGHT_CURLY_BRACKET, new Position()),
                new EmptyToken(TokenType.EOF, new Position())));
        Lexer lexer = new LexerMock(tokenList);
        Parser parser = new ParserImpl(lexer, new ErrorHandler());
        Program program = parser.parse();
        FunctionDefinition testFun = program.functions().get("testFun");
        Block block = testFun.block();
        assertTrue(block.statements().get(0) instanceof MultiplyExpression);
        assertEquals(new MultiplyExpression(new AddExpression(new IntNumber(2), new IntNumber(4)), new IntNumber(5)),
                block.statements().get(0));
    }
}

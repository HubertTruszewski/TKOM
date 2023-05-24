package pl.truszewski.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import pl.truszewski.ErrorHandler;
import pl.truszewski.lexer.Lexer;
import pl.truszewski.lexer.LexerImpl;
import pl.truszewski.programstructure.basic.Block;
import pl.truszewski.programstructure.basic.FunctionDefinition;
import pl.truszewski.programstructure.basic.Parameter;
import pl.truszewski.programstructure.basic.Program;
import pl.truszewski.programstructure.basic.ValueType;
import pl.truszewski.programstructure.expression.AccessExpression;
import pl.truszewski.programstructure.expression.AddExpression;
import pl.truszewski.programstructure.expression.AndExpression;
import pl.truszewski.programstructure.expression.DivideExpression;
import pl.truszewski.programstructure.expression.DoubleNumber;
import pl.truszewski.programstructure.expression.Expression;
import pl.truszewski.programstructure.expression.FunctionCall;
import pl.truszewski.programstructure.expression.GreaterEqualExpression;
import pl.truszewski.programstructure.expression.GreaterExpression;
import pl.truszewski.programstructure.expression.IdentifierExpression;
import pl.truszewski.programstructure.expression.InequalityExpression;
import pl.truszewski.programstructure.expression.IntNumber;
import pl.truszewski.programstructure.expression.LessEqualExpression;
import pl.truszewski.programstructure.expression.MultiplyExpression;
import pl.truszewski.programstructure.expression.NegationExpression;
import pl.truszewski.programstructure.expression.OrExpression;
import pl.truszewski.programstructure.expression.StringExpression;
import pl.truszewski.programstructure.expression.SubtractExpression;
import pl.truszewski.programstructure.statements.AssignmentStatement;
import pl.truszewski.programstructure.statements.DeclarationStatement;
import pl.truszewski.programstructure.statements.IfStatement;
import pl.truszewski.programstructure.statements.ReturnStatement;
import pl.truszewski.programstructure.statements.WhileStatement;
import pl.truszewski.source.Source;

public class ParserImplIntegrationTest {
    @Test
    public void testProvidedFile() throws FileNotFoundException {
        ErrorHandler errorHandler = new ErrorHandler();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/parsertestfile.txt"));
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Parser parser = new ParserImpl(lexer, errorHandler);
        Program program = parser.parse();
        assertEquals(2, program.functions().size());
        // tests for program
        assertTrue(program.functions().keySet().containsAll(Set.of("main", "someFunction")));
        // tests for someFunction
        FunctionDefinition someFunction = program.functions().get("someFunction");
        assertEquals("someFunction", someFunction.name());
        assertEquals(2, someFunction.parameters().size());
        assertEquals(2, someFunction.block().statements().size());
        assertEquals(ValueType.INT, someFunction.returnValueType());
        // tests for someFunction parameters
        Parameter firstSomeFunctionParameter = someFunction.parameters().get(0);
        assertEquals("a", firstSomeFunctionParameter.name());
        assertEquals(ValueType.INT, firstSomeFunctionParameter.type());
        Parameter secondSomeFunctionParameter = someFunction.parameters().get(1);
        assertEquals("b", secondSomeFunctionParameter.name());
        assertEquals(ValueType.INT, secondSomeFunctionParameter.type());
        // tests for someFunction block
        Block someFunctionBlock = someFunction.block();
        // tests for first declaration
        assertTrue(someFunctionBlock.statements().get(0) instanceof DeclarationStatement);
        DeclarationStatement declarationStatement = (DeclarationStatement) someFunctionBlock.statements().get(0);
        assertEquals("sum", declarationStatement.name());
        assertEquals(ValueType.INT, declarationStatement.valueType());
        assertTrue(declarationStatement.expression() instanceof AddExpression);
        AddExpression addExpression = (AddExpression) declarationStatement.expression();
        assertEquals(new IdentifierExpression("a"), addExpression.left());
        assertEquals(new IdentifierExpression("b"), addExpression.right());
        // tests for if statement
        assertTrue(someFunctionBlock.statements().get(1) instanceof IfStatement);
        IfStatement ifStatement = (IfStatement) someFunctionBlock.statements().get(1);
        assertEquals(new GreaterExpression(new IdentifierExpression("a"), new IdentifierExpression("b")),
                ifStatement.expression());
        assertEquals(new Block(List.of(new ReturnStatement(new IdentifierExpression("a")))), ifStatement.trueBlock());
        assertEquals(new Block(List.of(new ReturnStatement(new IdentifierExpression("sum")))), ifStatement.elseBlock());
        // tests for main function
        FunctionDefinition main = program.functions().get("main");
        assertEquals("main", main.name());
        assertEquals(0, main.parameters().size());
        assertEquals(4, main.block().statements().size());
        assertEquals(ValueType.VOID, main.returnValueType());
        // tests for main block
        Block mainBlock = main.block();
        // tests for first declaration
        assertTrue(mainBlock.statements().get(0) instanceof DeclarationStatement);
        DeclarationStatement declarationStatement1 = (DeclarationStatement) mainBlock.statements().get(0);
        assertEquals(new DeclarationStatement(ValueType.INT, "a", new IntNumber(5)), declarationStatement1);
        // tests for second declaration
        assertTrue(mainBlock.statements().get(1) instanceof DeclarationStatement);
        DeclarationStatement declarationStatement2 = (DeclarationStatement) mainBlock.statements().get(1);
        assertEquals(new DeclarationStatement(ValueType.INT,
                "x",
                new SubtractExpression(new FunctionCall("someFunction", List.of(new IntNumber(2), new IntNumber(3))),
                        new IntNumber(5))), declarationStatement2);
        // tests for while statement
        assertTrue(mainBlock.statements().get(2) instanceof WhileStatement);
        WhileStatement whileStatement = (WhileStatement) mainBlock.statements().get(2);
        assertEquals(new GreaterExpression(new IdentifierExpression("a"), new IntNumber(3)),
                whileStatement.expression());
        assertEquals(new Block(List.of(new AssignmentStatement("a",
                new SubtractExpression(new IdentifierExpression("a"), new IntNumber(1))))), whileStatement.codeBlock());
        // tests for return statement
        assertTrue(mainBlock.statements().get(3) instanceof ReturnStatement);
        ReturnStatement returnStatement = (ReturnStatement) mainBlock.statements().get(3);
        assertEquals(new ReturnStatement(null), returnStatement);
    }

    @Test
    public void parseTestFile() throws FileNotFoundException {
        ErrorHandler errorHandler = new ErrorHandler();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/testfile.txt"));
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Parser parser = new ParserImpl(lexer, errorHandler);
        Program program = parser.parse();
        assertEquals(2, program.functions().size());
        assertTrue(program.functions().keySet().containsAll(Set.of("main", "sum")));
        // tests for sum function
        FunctionDefinition sumFunction = program.functions().get("sum");
        assertEquals("sum", sumFunction.name());
        assertEquals(ValueType.INT, sumFunction.returnValueType());
        assertEquals(List.of(new Parameter(ValueType.INT, "x"), new Parameter(ValueType.INT, "y")),
                sumFunction.parameters());
        assertEquals(5, sumFunction.block().statements().size());
        Block sumBlock = sumFunction.block();
        assertEquals(new DeclarationStatement(ValueType.STRING, "text", new StringExpression("abcdef")),
                sumBlock.statements().get(0));
        assertEquals(new DeclarationStatement(ValueType.INT,
                        "x",
                        new AddExpression(new IntNumber(4),
                                new AddExpression(new IntNumber(6), new DivideExpression(new IntNumber(5), new IntNumber(1))))),
                sumBlock.statements().get(1));
        assertEquals(new DeclarationStatement(ValueType.INT, "z", new NegationExpression(new IntNumber(4))), sumBlock.statements().get(2));
        assertEquals(new DeclarationStatement(ValueType.BOOL,
                "y",
                new NegationExpression(new IdentifierExpression("xyz"))), sumBlock.statements().get(3));
        assertEquals(new ReturnStatement(new AddExpression(new AddExpression(new IdentifierExpression("x"),
                new IdentifierExpression("y")), new IntNumber(5))), sumBlock.statements().get(4));
        // tests for main function
        FunctionDefinition mainFunction = program.functions().get("main");
        assertEquals("main", mainFunction.name());
        assertEquals(ValueType.VOID, mainFunction.returnValueType());
        assertEquals(8, mainFunction.block().statements().size());
        assertEquals(List.of(new Parameter(ValueType.INT, "a"), new Parameter(ValueType.INT, "b")),
                mainFunction.parameters());
        Block mainBlock = mainFunction.block();
        assertEquals(new DeclarationStatement(ValueType.INT,
                "d",
                new AddExpression(new IntNumber(4), new IntNumber(5))), mainBlock.statements().get(0));
        assertEquals(new DeclarationStatement(ValueType.INT,
                "h",
                new FunctionCall("sum", List.of(new IntNumber(3), new IntNumber(4)))), mainBlock.statements().get(1));
        assertEquals(new DeclarationStatement(ValueType.DOUBLE, "p", new NegationExpression(new DoubleNumber(5.45))),
                mainBlock.statements().get(2));
        Block whileBlock = new Block(List.of(new AssignmentStatement("h",
                        new AddExpression(new IdentifierExpression("h"),
                                new FunctionCall("sum", List.of(new IdentifierExpression("d"), new IntNumber(3))))),
                new AssignmentStatement("d", new SubtractExpression(new IdentifierExpression("d"), new IntNumber(1)))));
        assertEquals(new WhileStatement(new GreaterEqualExpression(new IdentifierExpression("d"), new IntNumber(1)),
                whileBlock), mainBlock.statements().get(3));
        Expression ifExpression = new OrExpression(new AndExpression(new LessEqualExpression(new IdentifierExpression(
                "h"), new IntNumber(40)), new GreaterExpression(new IdentifierExpression("h"), new IntNumber(20))),
                new InequalityExpression(new IdentifierExpression("h"), new IntNumber(10)));
        assertEquals(new IfStatement(ifExpression,
                        new Block(List.of(new AssignmentStatement("p", new DoubleNumber(4.5)))),
                        new Block(List.of(new AssignmentStatement("p", new DoubleNumber(10.125))))),
                mainBlock.statements().get(4));
        assertEquals(new AssignmentStatement("d",
                        new MultiplyExpression(new IntNumber(5),
                                new AddExpression(new IntNumber(2),
                                        new DivideExpression(new IntNumber(10), new IntNumber(2))))),
                mainBlock.statements().get(5));
        assertEquals(new DeclarationStatement(ValueType.INT,
                        "r",
                        new AccessExpression(new IdentifierExpression("sphere"), new IdentifierExpression("r"))),
                mainBlock.statements().get(6));
        assertEquals(new DeclarationStatement(ValueType.INT,
                        "volume",
                        new AccessExpression(new IdentifierExpression("sphere"), new FunctionCall("volume", List.of()))),
                mainBlock.statements().get(7));
    }
}

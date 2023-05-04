package pl.truszewski.lexer;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import org.junit.Test;

import pl.truszewski.ErrorHandler;
import pl.truszewski.source.Source;
import pl.truszewski.token.Token;
import pl.truszewski.token.TokenType;

public class LexerImplTest {

    @Test
    public void buildLeftRoundBracket() {
        BufferedReader reader = new BufferedReader(new StringReader("("));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.LEFT_ROUND_BRACKET, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildRightRoundBracket() {
        BufferedReader reader = new BufferedReader(new StringReader(")"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.RIGHT_ROUND_BRACKET, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildLeftSquareBracket() {
        BufferedReader reader = new BufferedReader(new StringReader("["));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.LEFT_SQUARE_BRACKET, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildRightSquareBracket() {
        BufferedReader reader = new BufferedReader(new StringReader("]"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.RIGHT_SQUARE_BRACKET, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildLeftCurlyBracket() {
        BufferedReader reader = new BufferedReader(new StringReader("{"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.LEFT_CURLY_BRACKET, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildRightCurlyBracket() {
        BufferedReader reader = new BufferedReader(new StringReader("}"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.RIGHT_CURLY_BRACKET, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildComma() {
        BufferedReader reader = new BufferedReader(new StringReader(","));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.COMMA, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildDot() {
        BufferedReader reader = new BufferedReader(new StringReader("."));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.DOT, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildEqualSign() {
        BufferedReader reader = new BufferedReader(new StringReader("="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.EQUAL_SIGN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildGreaterSign() {
        BufferedReader reader = new BufferedReader(new StringReader(">"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.GREATER_SIGN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildLessSign() {
        BufferedReader reader = new BufferedReader(new StringReader("<"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.LESS_SIGN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildExclamationMark() {
        BufferedReader reader = new BufferedReader(new StringReader("!"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.EXCLAMATION_MARK, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildSemicolon() {
        BufferedReader reader = new BufferedReader(new StringReader(";"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.SEMICOLON, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildPlusSign() {
        BufferedReader reader = new BufferedReader(new StringReader("+"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.PLUS_SIGN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildMinusSign() {
        BufferedReader reader = new BufferedReader(new StringReader("-"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.MINUS_SIGN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildAsterisk() {
        BufferedReader reader = new BufferedReader(new StringReader("*"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.ASTERISK_SIGN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildSlash() {
        BufferedReader reader = new BufferedReader(new StringReader("/"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.SLASH, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildBackSlash() {
        BufferedReader reader = new BufferedReader(new StringReader("\\"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.BACKSLASH, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildGreaterOrEqual() {
        BufferedReader reader = new BufferedReader(new StringReader(">="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.GREATER_OR_EQUAL, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildIdentifier() {
        BufferedReader reader = new BufferedReader(new StringReader("abc ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.IDENTIFIER, token.getTokenType());
        assertEquals("abc", token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildIdentifierWithDigits() {
        BufferedReader reader = new BufferedReader(new StringReader("ab34c"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.IDENTIFIER, token.getTokenType());
        assertEquals("ab34c", token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildReturnKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("return;"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.RETURN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildWhileKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("while("));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.WHILE, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildIfKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("if("));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.IF, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildAsKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("as"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.AS, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildIntegerKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("int a ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.INTEGER, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildStringKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("string a ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.STRING, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildDoubleKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("double a ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.DOUBLE, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildBoolKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("bool a ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.BOOL, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildVoidKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("void"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.VOID, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildConeKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("Cone c ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.CONE, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildCylinderKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("Cylinder c ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.CYLINDER, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildSphereKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("Sphere s ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.SPHERE, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildCuboidKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  Cuboid s = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.CUBOID, token.getTokenType());
        assertEquals(3, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildPyramidKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("Pyramid p ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.PYRAMID, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildScreenKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("Screen s ="));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.SCREEN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildIntegerNumber() {
        BufferedReader reader = new BufferedReader(new StringReader("124"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.INT_NUMBER, token.getTokenType());
        assertEquals(124, token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildDoubleNumber() {
        BufferedReader reader = new BufferedReader(new StringReader("45.98"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.DOUBLE_NUMBER, token.getTokenType());
        assertEquals(45.98, token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildEOF() {
        BufferedReader reader = new BufferedReader(new StringReader("abc 124"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.IDENTIFIER, token.getTokenType());
        assertEquals("abc", token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.INT_NUMBER, token.getTokenType());
        assertEquals(124, token.getValue());
        assertEquals(5, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.EOF, token.getTokenType());
        assertEquals(8, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildEOFEmptyFile() {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.EOF, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildString() {
        BufferedReader reader = new BufferedReader(new StringReader("\"Ala ma kota\""));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.TEXT, token.getTokenType());
        assertEquals("Ala ma kota", token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildStringWithQuotationMark() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/teststringfile.txt"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.TEXT, token.getTokenType());
        assertEquals("Ala ma \" kota", token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildComment() {
        BufferedReader reader = new BufferedReader(new StringReader("// ala ma kota"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.COMMENT, token.getTokenType());
        assertEquals(" ala ma kota", token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildComplexIf() {
        BufferedReader reader = new BufferedReader(new StringReader("if(number>=5)"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.IF, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.LEFT_ROUND_BRACKET, token.getTokenType());
        assertEquals(3, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.IDENTIFIER, token.getTokenType());
        assertEquals("number", token.getValue());
        assertEquals(4, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.GREATER_OR_EQUAL, token.getTokenType());
        assertEquals(10, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.INT_NUMBER, token.getTokenType());
        assertEquals(5, token.getValue());
        assertEquals(12, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.RIGHT_ROUND_BRACKET, token.getTokenType());
        assertEquals(13, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildDeclarationWithInitialization() {
        BufferedReader reader = new BufferedReader(new StringReader("string text = \"ala ma kota\""));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.STRING, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.IDENTIFIER, token.getTokenType());
        assertEquals("text", token.getValue());
        assertEquals(8, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.EQUAL_SIGN, token.getTokenType());
        assertEquals(13, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.TEXT, token.getTokenType());
        assertEquals("ala ma kota", token.getValue());
        assertEquals(15, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildWhileLoop() {
        BufferedReader reader = new BufferedReader(new StringReader("while(1)"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.WHILE, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.LEFT_ROUND_BRACKET, token.getTokenType());
        assertEquals(6, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.INT_NUMBER, token.getTokenType());
        assertEquals(1, token.getValue());
        assertEquals(7, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.RIGHT_ROUND_BRACKET, token.getTokenType());
        assertEquals(8, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildMathExpression() {
        BufferedReader reader = new BufferedReader(new StringReader("5 + 9 * 5"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.INT_NUMBER, token.getTokenType());
        assertEquals(5, token.getValue());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.PLUS_SIGN, token.getTokenType());
        assertEquals(3, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.INT_NUMBER, token.getTokenType());
        assertEquals(9, token.getValue());
        assertEquals(5, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.ASTERISK_SIGN, token.getTokenType());
        assertEquals(7, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
        token = lexer.next();
        assertEquals(TokenType.INT_NUMBER, token.getTokenType());
        assertEquals(5, token.getValue());
        assertEquals(9, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildTooLargeIntNumber() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new StringReader("981234981234981234981234981234981234"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.UKNKOWN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }

    @Test
    public void buildTooLargeDoubleNumber() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new StringReader("4738.987678397338393987"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        Token token = lexer.next();
        assertEquals(TokenType.UKNKOWN, token.getTokenType());
        assertEquals(1, token.getPosition().getColumn());
        assertEquals(1, token.getPosition().getRow());
    }
}

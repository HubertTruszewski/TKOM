package pl.truszewski.lexer;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

import org.junit.Test;

import pl.truszewski.ErrorHandler;
import pl.truszewski.source.Source;
import pl.truszewski.token.TokenType;

public class LexerImplTest {

    @Test
    public void buildLeftRoundBracket() {
        BufferedReader reader = new BufferedReader(new StringReader("  ( "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.LEFT_ROUND_BRACKET, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildRightRoundBracket() {
        BufferedReader reader = new BufferedReader(new StringReader(" ) "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.RIGHT_ROUND_BRACKET, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildLeftSquareBracket() {
        BufferedReader reader = new BufferedReader(new StringReader(" [ "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.LEFT_SQUARE_BRACKET, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildRightSquareBracket() {
        BufferedReader reader = new BufferedReader(new StringReader(" ] "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.RIGHT_SQUARE_BRACKET, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildLeftCurlyBracket() {
        BufferedReader reader = new BufferedReader(new StringReader(" { "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.LEFT_CURLY_BRACKET, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildRightCurlyBracket() {
        BufferedReader reader = new BufferedReader(new StringReader(" } "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.RIGHT_CURLY_BRACKET, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildComma() {
        BufferedReader reader = new BufferedReader(new StringReader(" , "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.COMMA, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildDot() {
        BufferedReader reader = new BufferedReader(new StringReader(" . "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.DOT, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildEqualSign() {
        BufferedReader reader = new BufferedReader(new StringReader(" = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.EQUAL_SIGN, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildGreaterSign() {
        BufferedReader reader = new BufferedReader(new StringReader(" > "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.GREATER_SIGN, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildLessSign() {
        BufferedReader reader = new BufferedReader(new StringReader(" < "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.LESS_SIGN, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildExclamationMark() {
        BufferedReader reader = new BufferedReader(new StringReader(" ! "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.EXCLAMATION_MARK, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildSemicolon() {
        BufferedReader reader = new BufferedReader(new StringReader(" ; "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.SEMICOLON, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildPlusSign() {
        BufferedReader reader = new BufferedReader(new StringReader(" + "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.PLUS_SIGN, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildMinusSign() {
        BufferedReader reader = new BufferedReader(new StringReader(" - "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.MINUS_SIGN, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildAsterisk() {
        BufferedReader reader = new BufferedReader(new StringReader(" * "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.ASTERISK_SIGN, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildSlash() {
        BufferedReader reader = new BufferedReader(new StringReader(" / "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.SLASH, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildBackSlash() {
        BufferedReader reader = new BufferedReader(new StringReader(" \\ "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.BACKSLASH, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildGreaterOrEqual() {
        BufferedReader reader = new BufferedReader(new StringReader(" >= "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.GREATER_OR_EQUAL, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildIdentifier() {
        BufferedReader reader = new BufferedReader(new StringReader(" abc = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.IDENTIFIER, lexer.token().getTokenType());
        assertEquals("abc", lexer.token().getValue());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildIdentifierWithDigits() {
        BufferedReader reader = new BufferedReader(new StringReader(" ab34c = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.IDENTIFIER, lexer.token().getTokenType());
        assertEquals("ab34c", lexer.token().getValue());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildReturnKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader(" return; "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.RETURN, lexer.token().getTokenType());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildWhileKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  while( "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.WHILE, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildIfKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  if( "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.IF, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildAsKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  as "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.AS, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildIntegerKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  int a = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.INTEGER, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildStringKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  string a = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.STRING, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildDoubleKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  double a = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.DOUBLE, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildBoolKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  bool a = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.BOOL, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildVoidKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  void "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.VOID, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildConeKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  Cone c = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.CONE, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildCylinderKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  Cylinder c = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.CYLINDER, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildSphereKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  Sphere s = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.SPHERE, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildCuboidKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  Cuboid s = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.CUBOID, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildPyramidKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  Pyramid p = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.PYRAMID, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildScreenKeyword() {
        BufferedReader reader = new BufferedReader(new StringReader("  Screen s = "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.SCREEN, lexer.token().getTokenType());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildIntegerNumber() {
        BufferedReader reader = new BufferedReader(new StringReader("  124 "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.INT_NUMBER, lexer.token().getTokenType());
        assertEquals(124, lexer.token().getValue());
        assertEquals(3, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildDoubleNumber() {
        BufferedReader reader = new BufferedReader(new StringReader("   45.98 "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.DOUBLE_NUMBER, lexer.token().getTokenType());
        assertEquals(45.98, lexer.token().getValue());
        assertEquals(4, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildEOF() {
        BufferedReader reader = new BufferedReader(new StringReader(" abc 124 "));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.IDENTIFIER, lexer.token().getTokenType());
        assertEquals("abc", lexer.token().getValue());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
        lexer.next();
        assertEquals(TokenType.INT_NUMBER, lexer.token().getTokenType());
        assertEquals(124, lexer.token().getValue());
        assertEquals(6, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
        lexer.next();
        assertEquals(TokenType.EOF, lexer.token().getTokenType());
        assertEquals(10, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildEOFEmptyFile() {
        BufferedReader reader = new BufferedReader(new StringReader(""));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.EOF, lexer.token().getTokenType());
        assertEquals(1, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildString() {
        BufferedReader reader = new BufferedReader(new StringReader(" \"Ala ma kota\""));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.TEXT, lexer.token().getTokenType());
        assertEquals("Ala ma kota", lexer.token().getValue());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildStringWithQuotationMark() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/teststringfile.txt"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.TEXT, lexer.token().getTokenType());
        assertEquals("Ala ma \" kota", lexer.token().getValue());
        assertEquals(1, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }

    @Test
    public void buildComment() {
        BufferedReader reader = new BufferedReader(new StringReader(" // ala ma kota"));
        ErrorHandler errorHandler = new ErrorHandler();
        Source source = new Source(reader, errorHandler);
        Lexer lexer = new LexerImpl(source, errorHandler);
        assertEquals(lexer.token(), null);
        lexer.next();
        assertEquals(TokenType.COMMENT, lexer.token().getTokenType());
        assertEquals(" ala ma kota", lexer.token().getValue());
        assertEquals(2, lexer.token().getPosition().getColumn());
        assertEquals(1, lexer.token().getPosition().getRow());
    }
}

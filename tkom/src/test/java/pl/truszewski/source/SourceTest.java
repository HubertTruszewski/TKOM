package pl.truszewski.source;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import pl.truszewski.ErrorHandler;
import pl.truszewski.token.Position;

public class SourceTest {

    @Test
    public void testGetCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "abc";
        Source source = new Source(new StringReader(input), errorHandler);
        assertEquals(source.getCharacter(), null);
        source.nextCharacter();
        assertEquals(source.getCharacter(), "a");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "b");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "c");
    }

    @Test
    public void testGetCharacterWithNewLine() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\nd";
        Source source = new Source(new StringReader(input), errorHandler);
        assertEquals(source.getCharacter(), null);
        source.nextCharacter();
        assertEquals(source.getCharacter(), "a");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\n");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "d");
    }

    @Test
    public void testGetCharacterWithDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\rf\n\re";
        Source source = new Source(new StringReader(input), errorHandler);
        assertEquals(source.getCharacter(), null);
        source.nextCharacter();
        assertEquals(source.getCharacter(), "a");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\n");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\r");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "f");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\n");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\r");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "e");
    }

    @Test
    public void testGetCharacterWithAnotherDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\r\nf\r\ne";
        Source source = new Source(new StringReader(input), errorHandler);
        assertEquals(source.getCharacter(), null);
        source.nextCharacter();
        assertEquals(source.getCharacter(), "a");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\r");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\n");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "f");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\r");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "\n");
        source.nextCharacter();
        assertEquals(source.getCharacter(), "e");
    }

    @Test
    public void testGetPosition() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "ab";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(p.getColumn(), 0);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 1);
    }

    @Test
    public void testGetPositionWithNewLine() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\nbc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(p.getColumn(), 0);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 2);
    }

    @Test
    public void testGetPositionWithDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\rb";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(p.getColumn(), 0);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 3);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 2);
    }

    @Test
    public void testGetPositionWithMultiNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\n\nb\nc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(p.getColumn(), 0);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 2);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 3);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 4);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 4);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 5);
    }

    @Test
    public void testGetPositionWithMultiDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\r\n\rb\n\rc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(p.getColumn(), 0);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 3);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 2);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 2);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 3);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 3);
        source.nextCharacter();
        assertEquals(p.getColumn(), 3);
        assertEquals(p.getRow(), 3);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 4);
    }

    @Test
    public void testGetPositionWithAnotherMultiDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\r\n\r\nb\r\nc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(p.getColumn(), 0);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 3);
        assertEquals(p.getRow(), 1);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 2);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 2);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 3);
        source.nextCharacter();
        assertEquals(p.getColumn(), 2);
        assertEquals(p.getRow(), 3);
        source.nextCharacter();
        assertEquals(p.getColumn(), 3);
        assertEquals(p.getRow(), 3);
        source.nextCharacter();
        assertEquals(p.getColumn(), 1);
        assertEquals(p.getRow(), 4);
    }

    @Test
    public void testIsEOF() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "abc";
        Source source = new Source(new StringReader(input), errorHandler);
        source.nextCharacter();
        source.nextCharacter();
        source.nextCharacter();
        source.nextCharacter();
        assertEquals(source.isEOF(), true);
    }

    @Test
    public void testIsEOFFalse() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "abcd";
        Source source = new Source(new StringReader(input), errorHandler);
        source.nextCharacter();
        source.nextCharacter();
        source.nextCharacter();
        source.nextCharacter();
        assertEquals(source.isEOF(), false);
    }

}

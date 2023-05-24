package pl.truszewski.source;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
        assertNull(source.getCharacter());
        source.nextCharacter();
        assertEquals("a", source.getCharacter());
        source.nextCharacter();
        assertEquals("b", source.getCharacter());
        source.nextCharacter();
        assertEquals("c", source.getCharacter());
    }

    @Test
    public void testGetCharacterWithNewLine() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\nd";
        Source source = new Source(new StringReader(input), errorHandler);
        assertNull(source.getCharacter());
        source.nextCharacter();
        assertEquals("a", source.getCharacter());
        source.nextCharacter();
        assertEquals("\n", source.getCharacter());
        source.nextCharacter();
        assertEquals("d", source.getCharacter());
    }

    @Test
    public void testGetCharacterWithDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\rf\n\re";
        Source source = new Source(new StringReader(input), errorHandler);
        assertNull(source.getCharacter());
        source.nextCharacter();
        assertEquals("a", source.getCharacter());
        source.nextCharacter();
        assertEquals("\n", source.getCharacter());
        source.nextCharacter();
        assertEquals("\r", source.getCharacter());
        source.nextCharacter();
        assertEquals("f", source.getCharacter());
        source.nextCharacter();
        assertEquals("\n", source.getCharacter());
        source.nextCharacter();
        assertEquals("\r", source.getCharacter());
        source.nextCharacter();
        assertEquals("e", source.getCharacter());
    }

    @Test
    public void testGetCharacterWithAnotherDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\r\nf\r\ne";
        Source source = new Source(new StringReader(input), errorHandler);
        assertNull(source.getCharacter());
        source.nextCharacter();
        assertEquals("a", source.getCharacter());
        source.nextCharacter();
        assertEquals("\r", source.getCharacter());
        source.nextCharacter();
        assertEquals("\n", source.getCharacter());
        source.nextCharacter();
        assertEquals("f", source.getCharacter());
        source.nextCharacter();
        assertEquals("\r", source.getCharacter());
        source.nextCharacter();
        assertEquals("\n", source.getCharacter());
        source.nextCharacter();
        assertEquals("e", source.getCharacter());
    }

    @Test
    public void testGetPosition() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "ab";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(0, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(1, p.getRow());
    }

    @Test
    public void testGetPositionWithNewLine() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\nbc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(0, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(2, p.getRow());
    }

    @Test
    public void testGetPositionWithDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\rb";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(0, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(3, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(2, p.getRow());
    }

    @Test
    public void testGetPositionWithMultiNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\n\nb\nc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(0, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(2, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(3, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(4, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(4, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(5, p.getRow());
    }

    @Test
    public void testGetPositionWithMultiDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\n\r\n\rb\n\rc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(0, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(3, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(2, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(2, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(3, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(3, p.getRow());
        source.nextCharacter();
        assertEquals(3, p.getColumn());
        assertEquals(3, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(4, p.getRow());
    }

    @Test
    public void testGetPositionWithAnotherMultiDoubleNewLineCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "a\r\n\r\nb\r\nc";
        Source source = new Source(new StringReader(input), errorHandler);
        Position p = source.getPosition();
        assertEquals(0, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(3, p.getColumn());
        assertEquals(1, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(2, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(2, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(3, p.getRow());
        source.nextCharacter();
        assertEquals(2, p.getColumn());
        assertEquals(3, p.getRow());
        source.nextCharacter();
        assertEquals(3, p.getColumn());
        assertEquals(3, p.getRow());
        source.nextCharacter();
        assertEquals(1, p.getColumn());
        assertEquals(4, p.getRow());
    }

    @Test
    public void testPeekNextCharacter() {
        ErrorHandler errorHandler = new ErrorHandler();
        String input = "abc";
        Source source = new Source(new StringReader(input), errorHandler);
        assertEquals("a", source.peekNextCharacter());
        source.nextCharacter();
        assertEquals("b", source.peekNextCharacter());
        source.nextCharacter();
        assertEquals("c", source.peekNextCharacter());
        source.nextCharacter();
        assertNull(source.peekNextCharacter());
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
        assertTrue(source.isEOF());
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
        assertFalse(source.isEOF());
    }

}
